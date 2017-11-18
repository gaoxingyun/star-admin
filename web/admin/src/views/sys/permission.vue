<template>
  <div class="app-container calendar-list-container">

    <div class="filter-container">
      <el-input style="width: 200px;" class="filter-item" placeholder="权限编号" v-model="listQuery.id" @keyup.enter.native="handleQuery">
      </el-input>

<el-button class="filter-item" type="primary" icon="search" @click="handleQuery">搜索</el-button>
<el-button class="filter-item" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="edit">添加</el-button>

<!--
      <el-button class="filter-item" type="primary" v-waves icon="search" @click="handleFilter">搜索</el-button>
      
      <el-button class="filter-item" type="primary" icon="document" @click="handleDownload">导出</el-button>
      <el-checkbox class="filter-item" @change='tableKey=tableKey+1' v-model="showAuditor">显示审核人</el-checkbox>
-->
    </div>


    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit highlight-current-row style="width: 100%">
      <el-table-column align="center" label='ID' width="95">
        <template scope="scope">
          {{scope.$index}}
        </template>
      </el-table-column>
      <el-table-column label="权限编号"  align="center">
        <template scope="scope">
          {{scope.row.id}}
        </template>
      </el-table-column>
      <el-table-column label="权限名"  align="center">
        <template scope="scope">
          <span>{{scope.row.name}}</span>
        </template>
      </el-table-column>
      <el-table-column label="权限详情"  align="center">
        <template scope="scope">
          <span>{{scope.row.detail}}</span>
        </template>
      </el-table-column>
       <el-table-column label="父权限编号"  align="center">
        <template scope="scope">
          {{scope.row.parent_id}}
        </template>
      </el-table-column>
      
      <el-table-column align="center" label="操作" width="150">
        <template scope="scope">
        <el-tooltip content="编辑" placement="top">
          <el-button size="small" type="info" icon="edit" @click="handleUpdate(scope.row)">
          </el-button>
          </el-tooltip>
          <el-tooltip content="删除" placement="top">
          <el-button size="small" type="danger" icon="delete" @click="handleDelete(scope.row)">
          </el-button>  
          </el-tooltip>
           </template>
      </el-table-column>
     
    </el-table>

    <div v-show="!listLoading" class="pagination-container">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="listQuery.page"
        :page-sizes="[5,10,20,30,50,100,1000]" :page-size="listQuery.size" layout="total, sizes, prev, pager, next, jumper" :page-count="page.total_page">
      </el-pagination>
    </div>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form class="small-space" :model="permission" :rules="rules" label-position="left" label-width="70px" style='width: 400px; margin-left:50px;'>        

        <el-form-item label="权限名" prop="name">
          <el-input v-model="permission.name" :disabled="dialogStatus!='create' && dialogStatus!='update'" placeholder="请输入权限名">
          </el-input>
        </el-form-item>

         <el-form-item label="权限描述" prop="detail">
          <el-input v-model="permission.detail" :disabled="dialogStatus!='create' && dialogStatus!='update'" placeholder="请输入权限描述">
          </el-input>
        </el-form-item>

        <el-form-item label="父权限编号" prop="parent_id">
          <el-input v-model="permission.parent_id" :disabled="dialogStatus!='create' && dialogStatus!='update'" placeholder="请输入父权限编号">
          </el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="create">确 定</el-button>
        <el-button v-else-if="dialogStatus=='update'" type="primary" @click="update">确 定</el-button>
        <el-button v-else-if="dialogStatus=='delete'" type="danger" @click="remove">删 除</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { getList,createPermission,deletePermission,updatePermission,queryPermission } from '@/api/sys/permission'

export default {
  data() {
    return {
      list: null,
      listLoading: true,
      
      page: {
        total_page: undefined,
      },

      listQuery: {
        page: 1,
        size: 20,
        name: undefined,
        sort: '+id'
      },
      permission: {
        name: undefined,
        detail: undefined,
        parent_id: undefined,
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '创建',
        delete: '删除'
      },
      statusOptions: [0, 1],
      rules: {
          name: [
            { required: true, message: '请输入权限名', trigger: 'blur' },
            { min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur' }
          ],
          detail: [
            { required: true, message: '请输入权限描述', trigger: 'blur' },
            { min: 1, max: 64, message: '长度在 1 到 64 个字符', trigger: 'blur' }
          ],
          parent_id: [
            { required: true, message: '请输入父权限编号', trigger: 'blur' }
          ],
      },
    };

  },
  filters: {
    statusFilter(status) {
      const statusMap = {
        '0': '正常',
        '1': '停用',
      }
      return statusMap[status]
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true 
      getList(this.listQuery).then(response => {
        this.page.total_page = response.data.total_page;
        this.list = response.data.items
        this.listLoading = false
      })
    },

    handleCreate() {
      this.resetPermission()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
    },

    handleDelete(row) {
      this.permission = Object.assign({}, row)
      this.dialogStatus = 'delete'
      this.dialogFormVisible = true
    },

    handleUpdate(row) {
      this.permission = Object.assign({}, row)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
    },

    handleFilter() {
      this.listQuery.page = 1
      this.fetchData()
    },

    handleQuery() {
      if(this.listQuery.id == null || this.listQuery.id == ''){
        this.fetchData()
        return
      }
      this.query();
    },
    
    handleSizeChange(val) {
      this.listQuery.size = val
      this.fetchData()
    },

    handleCurrentChange(val) {
      this.listQuery.page = val;
      this.fetchData()
    },

    create(){
      createPermission(this.permission).then(response => {
        if(response.code = 200){
          this.dialogFormVisible = false
          this.handleFilter()
        }
      })
    },

    remove(){
      deletePermission(this.permission).then(response => {
        if(response.code = 200){
          this.dialogFormVisible = false 
          this.handleFilter()
        }
      })
    },

    update(){
      updatePermission(this.permission).then(response => {
        if(response.code = 200){
          this.dialogFormVisible = false
          this.handleFilter()
        }
      })
    },

    query(){
      queryPermission(this.listQuery).then(response => {
        if(response.code = 200){
          this.listLoading = true
          this.list = response.data.items
          this.listLoading = false
        }
      })
    },

    resetPermission() {
      this.permission = {
        name: undefined,
        detail: undefined,
        parent_id: undefined,
      }
    },
  },
}
</script>
