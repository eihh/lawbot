<template>
    <div class="chat-history-container">
      <!-- 侧边栏 - 会话列表 -->
      <div class="sidebar">
        <div class="sidebar-header">
          <h2>历史会话</h2>
          <button @click="createNewChat" class="new-chat-btn">
            <i class="el-icon-plus"></i> 新会话
          </button>
        </div>
        <div class="search-box">
          <el-input
            placeholder="搜索历史会话..."
            v-model="searchQuery"
            prefix-icon="el-icon-search"
            clearable
          ></el-input>
        </div>
        <div class="chat-list">
          <div
            v-for="chat in filteredChats"
            :key="chat.id"
            class="chat-item"
            :class="{ active: activeChatId === chat.id }"
            @click="selectChat(chat.id)"
          >
            <div class="chat-item-content">
              <div class="chat-title">{{ chat.title || '无标题会话' }}</div>
              <div class="chat-preview">{{ getLastMessagePreview(chat) }}</div>
              <div class="chat-meta">
                <span class="chat-time">{{ formatTime(chat.updatedAt) }}</span>
                <span class="chat-count">{{ chat.messages.length }}条</span>
              </div>
            </div>
            <el-button
              type="text"
              icon="el-icon-delete"
              class="delete-btn"
              @click.stop="deleteChat(chat.id)"
            ></el-button>
          </div>
        </div>
      </div>
  
      <!-- 主内容区 - 会话内容 -->
      <div class="main-content">
        <div v-if="activeChat" class="chat-area">
          <div class="chat-header">
            <h3>{{ activeChat.title || '无标题会话' }}</h3>
            <div class="chat-actions">
              <el-button type="text" icon="el-icon-edit" @click="editTitle">编辑标题</el-button>
              <el-button type="text" icon="el-icon-delete" @click="deleteChat(activeChatId)">删除</el-button>
            </div>
          </div>
          <div class="messages-container">
            <div
              v-for="(message, index) in activeChat.messages"
              :key="index"
              class="message"
              :class="message.sender"
            >
              <div class="message-avatar">
                <el-avatar :icon="message.sender === 'user' ? 'el-icon-user' : 'el-icon-chat-line-round'"></el-avatar>
              </div>
              <div class="message-content">
                <div class="message-sender">{{ message.sender === 'user' ? '我' : 'AI助手' }}</div>
                <div class="message-text">{{ message.text }}</div>
                <div class="message-time">{{ formatTime(message.time) }}</div>
              </div>
            </div>
          </div>
        </div>
        <div v-else class="empty-state">
          <el-empty description="选择或创建一个新会话"></el-empty>
        </div>
      </div>
  
      <!-- 编辑标题对话框 -->
      <el-dialog title="编辑会话标题" :visible.sync="editDialogVisible" width="30%">
        <el-input v-model="editedTitle" placeholder="请输入会话标题"></el-input>
        <span slot="footer" class="dialog-footer">
          <el-button @click="editDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveTitle">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </template>
  
  <script>
  export default {
    name: 'ChatHistory',
    data() {
      return {
        chats: [
          {
            id: '1',
            title: 'Vue2项目问题',
            updatedAt: new Date('2023-05-15T10:30:00'),
            messages: [
              {
                sender: 'user',
                text: '如何在Vue2中实现响应式数据？',
                time: new Date('2023-05-15T10:30:00')
              },
              {
                sender: 'assistant',
                text: '在Vue2中，你可以使用data选项来声明响应式数据。Vue会在初始化时对这些数据进行getter/setter转换，使其能够响应变化。',
                time: new Date('2023-05-15T10:32:00')
              }
            ]
          },
          {
            id: '2',
            title: 'JavaScript闭包问题',
            updatedAt: new Date('2023-05-10T14:15:00'),
            messages: [
              {
                sender: 'user',
                text: '能解释下JavaScript中的闭包吗？',
                time: new Date('2023-05-10T14:15:00')
              },
              {
                sender: 'assistant',
                text: '闭包是指有权访问另一个函数作用域中变量的函数。创建闭包的常见方式是在一个函数内部创建另一个函数。',
                time: new Date('2023-05-10T14:17:00')
              },
              {
                sender: 'user',
                text: '有什么实际应用场景吗？',
                time: new Date('2023-05-10T14:20:00')
              },
              {
                sender: 'assistant',
                text: '闭包常用于数据封装、模块模式、函数工厂等场景，比如创建私有变量、实现柯里化等。',
                time: new Date('2023-05-10T14:22:00')
              }
            ]
          },
          {
            id: '3',
            title: '',
            updatedAt: new Date('2023-05-05T09:00:00'),
            messages: [
              {
                sender: 'user',
                text: '今天天气怎么样？',
                time: new Date('2023-05-05T09:00:00')
              }
            ]
          }
        ],
        activeChatId: null,
        searchQuery: '',
        editDialogVisible: false,
        editedTitle: ''
      };
    },
    computed: {
      filteredChats() {
        if (!this.searchQuery) {
          return this.chats.slice().sort((a, b) => b.updatedAt - a.updatedAt);
        }
        return this.chats
          .filter(chat => {
            const titleMatch = chat.title && chat.title.toLowerCase().includes(this.searchQuery.toLowerCase());
            const contentMatch = chat.messages.some(msg => 
              msg.text.toLowerCase().includes(this.searchQuery.toLowerCase())
            );
            return titleMatch || contentMatch;
          })
          .sort((a, b) => b.updatedAt - a.updatedAt);
      },
      activeChat() {
        return this.chats.find(chat => chat.id === this.activeChatId);
      }
    },
    methods: {
      selectChat(chatId) {
        this.activeChatId = chatId;
      },
      createNewChat() {
        const newChat = {
          id: Date.now().toString(),
          title: '',
          updatedAt: new Date(),
          messages: []
        };
        this.chats.unshift(newChat);
        this.activeChatId = newChat.id;
      },
      deleteChat(chatId) {
        this.$confirm('确定要删除此会话吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          const index = this.chats.findIndex(chat => chat.id === chatId);
          if (index !== -1) {
            this.chats.splice(index, 1);
            if (this.activeChatId === chatId) {
              this.activeChatId = null;
            }
          }
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
      },
      editTitle() {
        this.editedTitle = this.activeChat.title || '';
        this.editDialogVisible = true;
      },
      saveTitle() {
        if (this.activeChat) {
          this.activeChat.title = this.editedTitle;
          this.activeChat.updatedAt = new Date();
          this.editDialogVisible = false;
          this.$message.success('标题已更新');
        }
      },
      getLastMessagePreview(chat) {
        if (chat.messages.length === 0) return '无消息';
        const lastMessage = chat.messages[chat.messages.length - 1];
        return lastMessage.text.length > 30 
          ? lastMessage.text.substring(0, 30) + '...' 
          : lastMessage.text;
      },
      formatTime(time) {
        if (!(time instanceof Date)) {
          time = new Date(time);
        }
        const now = new Date();
        const diffDays = Math.floor((now - time) / (1000 * 60 * 60 * 24));
        
        if (diffDays === 0) {
          return time.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
        } else if (diffDays === 1) {
          return '昨天';
        } else if (diffDays < 7) {
          return `${diffDays}天前`;
        } else {
          return time.toLocaleDateString([], { month: 'short', day: 'numeric' });
        }
      }
    },
    mounted() {
      // 默认选中第一个会话
      if (this.chats.length > 0) {
        this.activeChatId = this.chats[0].id;
      }
    }
  };
  </script>
  
  <style scoped>
  .chat-history-container {
    display: flex;
    height: 100vh;
    background-color: #f5f5f5;
  }
  
  .sidebar {
    width: 300px;
    background-color: #fff;
    border-right: 1px solid #e6e6e6;
    display: flex;
    flex-direction: column;
    height: 100%;
  }
  
  .sidebar-header {
    padding: 15px;
    border-bottom: 1px solid #e6e6e6;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .sidebar-header h2 {
    margin: 0;
    font-size: 18px;
  }
  
  .new-chat-btn {
    background-color: #409eff;
    color: white;
    border: none;
    padding: 5px 10px;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
  }
  
  .new-chat-btn:hover {
    background-color: #66b1ff;
  }
  
  .search-box {
    padding: 15px;
    border-bottom: 1px solid #e6e6e6;
  }
  
  .chat-list {
    flex: 1;
    overflow-y: auto;
  }
  
  .chat-item {
    padding: 12px 15px;
    border-bottom: 1px solid #f0f0f0;
    cursor: pointer;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .chat-item:hover {
    background-color: #f9f9f9;
  }
  
  .chat-item.active {
    background-color: #ebf5ff;
  }
  
  .chat-item-content {
    flex: 1;
    overflow: hidden;
  }
  
  .chat-title {
    font-weight: bold;
    margin-bottom: 5px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
  
  .chat-preview {
    color: #666;
    font-size: 13px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    margin-bottom: 5px;
  }
  
  .chat-meta {
    display: flex;
    justify-content: space-between;
    font-size: 12px;
    color: #999;
  }
  
  .delete-btn {
    color: #999;
    font-size: 16px;
    padding: 5px;
  }
  
  .delete-btn:hover {
    color: #f56c6c;
  }
  
  .main-content {
    flex: 1;
    display: flex;
    flex-direction: column;
    height: 100%;
  }
  
  .chat-area {
    display: flex;
    flex-direction: column;
    height: 100%;
  }
  
  .chat-header {
    padding: 15px;
    border-bottom: 1px solid #e6e6e6;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .chat-header h3 {
    margin: 0;
  }
  
  .messages-container {
    flex: 1;
    padding: 20px;
    overflow-y: auto;
    background-color: #fff;
  }
  
  .message {
    display: flex;
    margin-bottom: 20px;
  }
  
  .message-avatar {
    margin-right: 15px;
  }
  
  .message-content {
    flex: 1;
  }
  
  .message-sender {
    font-weight: bold;
    margin-bottom: 5px;
  }
  
  .message-text {
    background-color: #f9f9f9;
    padding: 10px 15px;
    border-radius: 5px;
    display: inline-block;
    max-width: 80%;
  }
  
  .message-time {
    font-size: 12px;
    color: #999;
    margin-top: 5px;
  }
  
  .message.user {
    flex-direction: row-reverse;
  }
  
  .message.user .message-avatar {
    margin-right: 0;
    margin-left: 15px;
  }
  
  .message.user .message-text {
    background-color: #409eff;
    color: white;
  }
  
  .message.user .message-content {
    text-align: right;
  }
  
  .empty-state {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    background-color: #fff;
  }
  </style>