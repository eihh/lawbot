<template>
  <div style="width: 100%; height: 50vh">
    <div
      class="container mx-auto px-4 py-8"
      style="
        margin-left: 50px;
        width: 100%;
        height: 80vh;
        overflow: auto;
        margin: 0 auto;
      "
    >
      <h1 class="text-3xl font-bold text-gray-800 mb-6 text-center">
        文章列表
      </h1>

      <div
        v-for="item in displayedItems"
        :key="item.id"
        class="bg-white rounded-xl border border-gray-200 shadow-md p-6 mb-6 transform transition-all duration-300 hover:shadow-xl hover:border-blue-300 hover:-translate-y-1"
        style="margin-top: 30px; padding: 10px; cursor: pointer"
        @click="showDetail(item)"
      >
        <div class="flex items-center mb-4">
          <div
            class="w-10 h-10 rounded-full bg-blue-100 flex items-center justify-center text-blue-500 mr-3"
          >
            <i class="fa fa-file-text-o"></i>
          </div>
          <h2 class="text-xl font-semibold text-gray-900 mb-0">
            {{ item.title }}
          </h2>
        </div>
        <p class="text-gray-600 mb-4 leading-relaxed line-clamp-3">
          {{ item.content }}
        </p>
        <div class="flex justify-between items-center">
          <button
            class="text-blue-500 hover:text-blue-700 font-medium transition-colors duration-300 flex items-center"
            @click.stop="showFullContent(item)"
          >
            阅读更多 <i class="fa fa-long-arrow-right ml-1"></i>
          </button>
        </div>
      </div>

      <div v-if="items.length > 3" class="mt-8 text-center">
        <button
          class="bg-gradient-to-r from-blue-500 to-indigo-600 hover:from-blue-600 hover:to-indigo-700 text-white font-medium py-2.5 px-8 rounded-lg transition-all duration-300 shadow-lg hover:shadow-xl transform hover:-translate-y-0.5 focus:ring-2 focus:ring-blue-400 focus:ring-opacity-50"
          @click="loadMore"
        >
          <i class="fa fa-plus-circle mr-2"></i>加载更多文章
        </button>
      </div>

      <!-- 使用el-dialog替换原有弹窗 -->
      <el-dialog
        :title="currentItem ? currentItem.title : ''"
        :visible.sync="showModal"
        width="40%"
        top="5vh"
        custom-class="article-dialog"
      >
        <div style="width: 100%; text-align: center; font-size: 30px">
          标题：{{ currentItem ? currentItem.title : "" }}
        </div>
        <div class="prose max-w-none mb-8">
          <p class="text-gray-700 whitespace-pre-line">
            {{ currentItem ? currentItem.content : "" }}
          </p>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button @click="printToPDF" type="primary">
            <i class="fa fa-file-pdf-o mr-2"></i>打印为PDF
          </el-button>
          <el-button @click="closeModal">关闭</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>
  
  <script>
export default {
  name: "GGGhhhHH",
  data() {
    return {
      items: [
        {
          id: 1,
          title: "Vue.js列表渲染技巧",
          content:
            "Vue.js提供了强大的列表渲染能力，通过v-for指令可以轻松地将数组数据渲染为DOM元素列表。在处理大型列表时，有许多优化技巧可以提高性能。例如，使用key属性来帮助Vue识别哪些元素发生了变化，从而最小化DOM操作。此外，还可以结合虚拟滚动技术来处理极大数量的数据列表，避免一次性渲染过多元素导致的性能问题。Vue.js提供了强大的列表渲染能力，通过v-for指令可以轻松地将数组数据渲染为DOM元素列表。在处理大型列表时，有许多优化技巧可以提高性能。例如，使用key属性来帮助Vue识别哪些元素发生了变化，从而最小化DOM操作。此外，还可以结合虚拟滚动技术来处理极大数量的数据列表，避免一次性渲染过多元素导致的性能问题。Vue.js提供了强大的列表渲染能力，通过v-for指令可以轻松地将数组数据渲染为DOM元素列表。在处理大型列表时，有许多优化技巧可以提高性能。例如，使用key属性来帮助Vue识别哪些元素发生了变化，从而最小化DOM操作。此外，还可以结合虚拟滚动技术来处理极大数量的数据列表，避免一次性渲染过多元素导致的性能问题。Vue.js提供了强大的列表渲染能力，通过v-for指令可以轻松地将数组数据渲染为DOM元素列表。在处理大型列表时，有许多优化技巧可以提高性能。例如，使用key属性来帮助Vue识别哪些元素发生了变化，从而最小化DOM操作。此外，还可以结合虚拟滚动技术来处理极大数量的数据列表，避免一次性渲染过多元素导致的性能问题。Vue.js提供了强大的列表渲染能力，通过v-for指令可以轻松地将数组数据渲染为DOM元素列表。在处理大型列表时，有许多优化技巧可以提高性能。例如，使用key属性来帮助Vue识别哪些元素发生了变化，从而最小化DOM操作。此外，还可以结合虚拟滚动技术来处理极大数量的数据列表，避免一次性渲染过多元素导致的性能问题。Vue.js提供了强大的列表渲染能力，通过v-for指令可以轻松地将数组数据渲染为DOM元素列表。在处理大型列表时，有许多优化技巧可以提高性能。例如，使用key属性来帮助Vue识别哪些元素发生了变化，从而最小化DOM操作。此外，还可以结合虚拟滚动技术来处理极大数量的数据列表，避免一次性渲染过多元素导致的性能问题。Vue.js提供了强大的列表渲染能力，通过v-for指令可以轻松地将数组数据渲染为DOM元素列表。在处理大型列表时，有许多优化技巧可以提高性能。例如，使用key属性来帮助Vue识别哪些元素发生了变化，从而最小化DOM操作。此外，还可以结合虚拟滚动技术来处理极大数量的数据列表，避免一次性渲染过多元素导致的性能问题。Vue.js提供了强大的列表渲染能力，通过v-for指令可以轻松地将数组数据渲染为DOM元素列表。在处理大型列表时，有许多优化技巧可以提高性能。例如，使用key属性来帮助Vue识别哪些元素发生了变化，从而最小化DOM操作。此外，还可以结合虚拟滚动技术来处理极大数量的数据列表，避免一次性渲染过多元素导致的性能问题。",
        },
        {
          id: 2,
          title: "响应式设计最佳实践",
          content:
            "在现代Web开Vue.js提供了强大的列表渲染能力，通过v-for指令可以轻松地将数组数据渲染为DOM元素列表。在处理大型列表时，有许多优化技巧可以提高性能。例如，使用key属性来帮助Vue识别哪些元素发生了变化，从而最小化DOM操作。此外，还可以结合虚拟滚动技术来处理极大数量的数据列表，避免一次性渲染过多元素导致的性能问题。发中，响应式设计是必不可少的。通过Tailwind CSS等工具，可以轻松实现跨设备的响应式布局。响应式设计不仅仅是调整元素大小，还包括在不同屏幕尺寸下提供最佳的用户体验。这可能涉及到重新排列元素、调整字体大小、甚至改变交互方式。在实现响应式设计时，应该优先考虑移动设备，然后逐步增强桌面体验。",
        },
        {
          id: 3,
          title: "JavaScript异步编程",
          content:
            "JavaScript的Vue.js提供了强大的列表渲染能力，通过v-for指令可以轻松地将数组数据渲染为DOM元素列表。在处理大型列表时，有许多优化技巧可以提高性能。例如，使用key属性来帮助Vue识别哪些元素发生了变化，从而最小化DOM操作。此外，还可以结合虚拟滚动技术来处理极大数量的数据列表，避免一次性渲染过多元素导致的性能问题。异步特性是其强大之处之一，但也常常是开发者困惑的来源。Promise、async/await等特性使得异步代码更加清晰易读。理解事件循环机制对于掌握JavaScript异步编程至关重要。当处理多个异步操作时，我们可以使用Promise.all来并行执行多个Promise，或者使用Promise.race来处理竞争条件。合理使用这些技术可以大大提高应用程序的性能和用户体验。",
        },
        {
          id: 4,
          title: "组件化开发思想",
          content:
            "组件化是现代前端开发Vue.js提供了强大的列表渲染能力，通过v-for指令可以轻松地将数组数据渲染为DOM元素列表。在处理大型列表时，有许多优化技巧可以提高性能。例如，使用key属性来帮助Vue识别哪些元素发生了变化，从而最小化DOM操作。此外，还可以结合虚拟滚动技术来处理极大数量的数据列表，避免一次性渲染过多元素导致的性能问题。的核心思想之一。通过将UI拆分为独立的、可复用的组件，可以提高代码的可维护性和可测试性。组件应该具有单一职责原则，每个组件只负责一个特定的功能或UI部分。Vue.js的组件系统使得创建和管理组件变得非常简单。父子组件之间通过props和events进行通信，而更复杂的状态管理可以通过Vuex或Pinia等库来实现。",
        },
        {
          id: 5,
          title: "前端性能优化策略",
          content:
            "前端性能直接影响用Vue.js提供了强大的列表渲染能力，通过v-for指令可以轻松地将数组数据渲染为DOM元素列表。在处理大型列表时，有许多优化技巧可以提高性能。例如，使用key属性来帮助Vue识别哪些元素发生了变化，从而最小化DOM操作。此外，还可以结合虚拟滚动技术来处理极大数量的数据列表，避免一次性渲染过多元素导致的性能问题。户体验和转化率。性能优化应该贯穿整个开发过程，而不是事后补救。常见的前端性能优化策略包括代码分割、懒加载、资源压缩、缓存策略、图片优化等。使用工具如Webpack、Vite等可以帮助自动化许多优化过程。此外，监控应用性能并持续改进也是保持高性能应用的关键。",
        },
      ],
      displayCount: 10,
      showModal: false,
      currentItem: null,
    };
  },
  computed: {
    displayedItems() {
      return this.items.slice(0, this.displayCount);
    },
  },
  methods: {
    showFullContent(item) {
      this.currentItem = item;
      this.showModal = true;
    },
    showDetail(item) {
      this.currentItem = item;
      this.showModal = true;
    },
    closeModal() {
      this.showModal = false;
    },
    loadMore() {
      this.displayCount = Math.min(this.displayCount + 3, this.items.length);
    },
    printToPDF() {
      // 创建一个新的窗口或iframe来打印
    },
  },
};
</script>
  
  <style scoped>
.container {
  max-width: 1200px;
}

/* 添加平滑过渡效果 */
.container {
  animation: fadeIn 0.6s ease-out;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(15px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 优化阅读体验的排版 */
h1 {
  letter-spacing: -0.025em;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
}

h2 {
  letter-spacing: -0.015em;
}

p {
  line-height: 1.625;
}

/* 自定义按钮悬停效果 */
button {
  outline: none;
}

button:focus {
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.5);
}

/* 列表项动画 */
div[class*="bg-white rounded-xl"] {
  transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
}

/* 三行文本限制样式 */
.line-clamp-3 {
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
}

/* 自定义el-dialog样式 */
.article-dialog {
  border-radius: 12px;
}

.article-dialog .el-dialog__body {
  padding: 20px 25px;
  color: #606266;
}

.article-dialog .el-dialog__header {
  border-bottom: 1px solid #e8e8e8;
  padding: 15px 20px;
  text-align: center;
}

.article-dialog .el-dialog__title {
  font-size: 1.5rem;
  font-weight: bold;
  color: #303133;
}

.article-dialog .el-dialog__footer {
  padding: 15px 20px;
  text-align: center;
  border-top: 1px solid #e8e8e8;
}
</style>