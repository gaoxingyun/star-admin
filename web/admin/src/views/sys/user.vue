<template>
  <div class="app-container calendar-list-container">

    <div class="filter-container">
      <el-input style="width: 200px;" class="filter-item" placeholder="用户名" v-model="listQuery.name" @keyup.enter.native="handleQuery">
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
      <el-table-column label="用户编号" width="110" align="center">
        <template scope="scope">
          {{scope.row.id}}
        </template>
      </el-table-column>
      <el-table-column label="用户名" width="110" align="center">
        <template scope="scope">
          <span>{{scope.row.name}}</span>
        </template>
      </el-table-column>
       <el-table-column label="父用户编号" width="110" align="center">
        <template scope="scope">
          {{scope.row.parent_id}}
        </template>
      </el-table-column>
      <el-table-column label="状态" width="110" align="center">
        <template scope="scope">
          <span>{{scope.row.status | statusFilter}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center"  label="最后登陆时间">
        <template scope="scope">
          <i class="el-icon-time"></i>
          <span>{{scope.row.last_login_time}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center"  label="创建时间" >
        <template scope="scope">
          <i class="el-icon-time"></i>
          <span>{{scope.row.create_time}}</span>
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
      <el-form class="small-space" :model="user" :rules="rules" label-position="left" label-width="70px" style='width: 400px; margin-left:50px;'>        

        <el-form-item label="用户名" prop="name">
          <el-input v-model="user.name" :disabled="dialogStatus!='create'" placeholder="请输入用户名">
          </el-input>
        </el-form-item>

        <el-form-item v-if="dialogStatus=='create'" label="密码" prop="password">
          <el-input v-model="user.password"  placeholder="请输入密码">
          </el-input>
        </el-form-item>


        <el-form-item label="状态" prop="status">
          <el-select class="filter-item" v-model="user.status" :disabled="dialogStatus!='create' && dialogStatus!='update'" placeholder="请选择">
            <el-option v-for="item in statusOptions" :key="item" :label="item | statusFilter" :value="item">
            </el-option>
          </el-select>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="create">确 定</el-button>
        <el-button v-else-if="dialogStatus=='update'" type="primary" @click="update">确 定</el-button>
        <el-button v-else-if="dialogStatus=='delete'"type="danger" @click="remove">删 除</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { getList,createUser,deleteUser,updateUser,queryUser } from '@/api/sys/user'

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
        status: undefined,
        sort: '+id'
      },
      user: {
        name: undefined,
        password: undefined,
        status: undefined,
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
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '请选择用户状态', trigger: 'blur' }
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
      this.resetUser()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
    },

    handleDelete(row) {
      this.user = Object.assign({}, row)
      this.dialogStatus = 'delete'
      this.dialogFormVisible = true
    },

    handleUpdate(row) {
      this.user = Object.assign({}, row)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
    },

    handleFilter() {
      this.listQuery.page = 1
      this.fetchData()
    },

    handleQuery() {
      if(this.listQuery.name == null || this.listQuery.name == ''){
        this.fetchData()
        return
      }
      this.query();
    },
    
    handleSizeChange(val) {
      this.listQuery.size = val
      console.log(`每页 ${val} 条`);
      this.fetchData()
    },

    handleCurrentChange(val) {
      this.listQuery.page = val;
      console.log(`当前页: ${val}`);
      this.fetchData()
    },

    create(){
      createUser(this.user).then(response => {
        if(response.code = 200){
          this.dialogFormVisible = false
          this.handleFilter()
        }
      })
    },

    remove(){
      deleteUser(this.user).then(response => {
        if(response.code = 200){
          this.dialogFormVisible = false 
          this.handleFilter()
        }
      })
    },

    update(){
      updateUser(this.user).then(response => {
        if(response.code = 200){
          this.dialogFormVisible = false
          this.handleFilter()
        }
      })
    },

    query(){
      queryUser(this.listQuery).then(response => {
        if(response.code = 200){
          this.listLoading = true
          this.list = response.data.items
          this.listLoading = false
        }
      })
    },

    resetUser() {
      this.user = {
        name: undefined,
        password: undefined,
        status: undefined,
      }
    },
  },
}
</script>
