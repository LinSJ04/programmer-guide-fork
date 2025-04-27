<div align="center">
    <img alt="logo" src="image/springai-reading.png" style="height: 80px">
</div>
<div align="center">
    <h2>深入Spring AI，从源码开始</h2>
    <h4>探索Java企业级AI应用框架，理解它的内部机制，带大家从入门到精通。</h4>
</div>
<p align="center">
    </a>
   <a href='https://gitee.com/itbeien/programmer-guide/stargazers'><img src='https://gitee.com/itbeien/programmer-guide/badge/star.svg?theme=dark' alt='star'></img></a>
    <a href='https://gitee.com/itbeien/programmer-guide/members'><img src='https://gitee.com/itbeien/programmer-guide/badge/fork.svg?theme=dark' alt='fork'></img></a>
    <a href="https://github.com/itbeien/programmer-guide"><img src="https://img.shields.io/github/stars/itbeien/programmer-guide?logo=github&logoColor=%23EF2D5E&label=Stars&labelColor=%23000000&color=%23EF2D5E&cacheSeconds=3600" alt="Stars Badge"/>
    <a href="https://github.com/itbeien/programmer-guide/fork"><img src="https://img.shields.io/github/forks/itbeien/programmer-guide?label=Forks&logo=github&logoColor=%23F2BB13&labelColor=%23BE2323&color=%23F2BB13" alt="Fork Badge">
</p>
<p align="center">
    <a href="https://itbeien.cn/linkme/link-me.html"><img src="https://img.shields.io/badge/WeChat-itbeien-%2307C160?logo=wechat" alt="Wechat"/></a>
   <a href="https://blog.csdn.net/BenMicro">
        <img src="../image/CSDN-red.svg" alt="CSDN"></a>
        <a href="https://juejin.cn/user/3386151545086157">
            <img src="../image/掘金-blue.svg" alt="掘金"/></a>
    <a href="https://www.yuque.com/u21261961/wufq8h">
        <img src="../image/语雀-green.svg" alt="语雀"/></a>
    <a href="https://vcnb783grhl8.feishu.cn/wiki/ZWYZw0z07i1KGakYYV2cjkUanG2">
        <img src="../image/飞书-8A2BE2.svg" alt="飞书"/></a>
    <a href="https://www.itbeien.cn">
        <img src="../image/博客-blue.svg" alt="博客"/></a>
</p>
<p align="center">
    <a href="#Java">Java</a>
    |
    <a href="#简介">简介</a>
    |
    <a href="#为何做Spring源码分析">Why</a>
    |
    🙏 <a href="#顺手点个Star">点个Star</a>
    |
    <a href="#spring AI">Spring AI</a>
    |
    💬 <a href="#与我联系">联系我</a>
    |
    <a href="#欢迎贡献">贡献</a>
    |
    🔄 <a href="#持续更新中">更新</a>
    |
    💻 <a href="#源码阅读">源码阅读</a>
</p>








## ⚡开发环境

<div align="left">
    <img src="https://img.shields.io/badge/Java-17%2B-%23437291?logo=openjdk&logoColor=%23437291"/>
    <img src="https://img.shields.io/badge/SpringAI-1.0.0SNAPSHOT-%23437291?logo=Spring&logoColor=%236DB33F&color=%236DB33F"/>
    <img src="https://img.shields.io/badge/SpringBoot-3.4.5-%23437291?logo=SpringBoot&logoColor=%236DB33F&color=%236DB33F"/>
    <img src="https://img.shields.io/badge/Maven-3.9.9-%23437291?logo=Apache%20Maven&logoColor=%23C71A36&color=%23C71A36"/>
    <img src="https://img.shields.io/badge/IDEA-2025.1-%23437291?logo=idea%20Maven&logoColor=%23C71A36&color=%23C71A36"/>
</div>



## 👋简介

大家好呀，我是贝恩聊架构。我是一名拥有10年＋经验的全栈程序员，也是一个对 AI充满热情的程序员。为了帮助那些希望深入了解AI的程序员们，我创建了这个 “Spring AI 源码阅读系列”📖。通过这个系列，我希望能够与你们共同探索 Spring AI的内部工作机制。如果您有同样的兴趣或问题，请联系我！

## **为何做Spring AI源码分析**

在我作为AI应用的开发者工作中，我经常遇到需要深入理解和调整框架的情况。这些工作不只是简单地使用框架API，更多地是需要对框架的内部工作机制有详细的了解。因此，我开始深入研究Spring AI的源码，希望能够更透彻地理解其内部的工作机制，以便更好地应用到我的实际工作中和产品中，提高开发AI应用产品的技术能力。分享我的源码分析📝，也是为了给那些希望真正理解AI应用框架，而不仅仅是使用它的开发者提供一些参考和帮助。

## 🙏顺手点个Star

亲爱的朋友们，我花了很多心思去研究和整理“Spring AI 源码阅读系列”📘。如果你觉得这东西还不错，或者给你带来了一点点帮助，麻烦点一下Star。这对我意义重大，每一个Star都能让我觉得所有的努力都是值得的。我知道这是小事一桩，但你的那一下点击，对我来说就是最好的鼓励。无论如何，都要感谢你抽时间阅读我的内容，真的很感激！

💬与我联系

**[个人网站](https://itbeien.cn/)|[CSDN](https://blog.csdn.net/BenMicro)| [掘金](https://juejin.cn/user/3386151545086157)|[公众号](https://itbeien.cn/planet/星球介绍/project.html)|[语雀](https://www.yuque.com/u21261961)|[飞书](https://vcnb783grhl8.feishu.cn/wiki/ZWYZw0z07i1KGakYYV2cjkUanG2)**|✉️ [Email](itbeien@163.com) |💬 [Issue](https://gitee.com/itbeien/programmer-guide/issues)

## 欢迎贡献

如果你发现任何错误或者有改进建议，欢迎提交 issue 或者 pull request。你的反馈对于我非常宝贵！

## 🔄持续更新中

为了给大家提供最新、最有价值的内容，我会坚持持续更新这个仓库。每一周，你都可以期待看到一些新的内容或者对已有内容的改进。如果你有任何建议或反馈，欢迎随时联系我。我非常重视每一个反馈，因为这是我持续改进的动力🚀。

## SpringAI-Reading

[🚀SpringAI-Reading(实时更新-语雀)🚀](https://www.yuque.com/u21261961/wufq8h/nneitxzhl0cktzdb)

[🚀SpringAI-Reading(实时更新-飞书)🚀](https://vcnb783grhl8.feishu.cn/wiki/T7UswZeKGiivX9kiK4LcYXdknSd)