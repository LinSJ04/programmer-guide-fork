# LangChain4j核心模块之ChatModel接口源码分析

<div align="center">
    <img alt="logo" src="https://gitee.com/itbeien/base/raw/master/images/langchain4j-reading.png" style="height: 80px">
</div>
<div align="center">
    <h2>深入LangChain4j，从源码开始</h2>
    <h4>探索Java企业级AI应用框架，理解它的内部机制，带大家从入门到精通</h4>
</div>


<div align="left">
    <img src="https://img.shields.io/badge/Java-17%2B-%23437291?logo=openjdk&logoColor=%23437291"/>
    <img src="https://img.shields.io/badge/LangChain4j-1.0.0beta4.SNAPSHOT-%23437291?logo=langchain4j&logoColor=%236DB33F&color=%236DB33F"/>
    <img src="https://img.shields.io/badge/SpringBoot-3.4.5-%23437291?logo=SpringBoot&logoColor=%236DB33F&color=%236DB33F"/>
    <img src="https://img.shields.io/badge/Maven-3.9.9-%23437291?logo=Apache%20Maven&logoColor=%23C71A36&color=%23C71A36"/>
    <img src="https://img.shields.io/badge/IDEA-2025.1-%23437291?logo=idea%20Maven&logoColor=%23C71A36&color=%23C71A36"/>
</div>




![](https://gitee.com/itbeien/base/raw/master/images/ChatModel接口源码分析.png)

**本文档源码实例基于DeepSeek大模型完成基本对话**

## 1 前置知识

本文中对象创建和参数设置使用到了建造者模式和链式调用

### 1.1 建造者设计模式

设计模式分为三大类：创建型模式 、结构型模式、行为型模式。建造者模式（Builder Pattern）是设计模式中的一种创建型设计模式，本篇源码分析中ChatModel对象的创建使用到了该设计模式。该模式主要适用于创建复杂对象，尤其是那些具有多个可选参数或需要分步骤构建的对象

### 1.2 链式调用

是一种编程技巧，通过方法返回自身对象，实现连续调用多个方法，使代码更加简洁和可读

## 2 ChatModel基本描述

ChatModel接口和相关实现类主要用于创建和管理与 OpenAI /DeepSeek/通义千问等聊天模型的交互

## 3 ChatModel源码分析

`ChatModel` 接口，它是一个具有聊天 API 的语言模型。该接口包含多个默认方法，用于与聊天模型进行交互。下面是对代码的详细解释：

### 3.1  `chat(ChatRequest chatRequest)` 方法

这是与聊天模型交互的主要 API。它接收一个 `ChatRequest` 对象，该对象包含所有输入到语言模型的信息，并返回一个 `ChatResponse` 对象，其中包含所有从语言模型输出的信息。

- **默认实现**：该方法首先创建一个 `finalChatRequest` 对象，该对象包含原始请求的消息和参数（参数会与默认请求参数合并）。
- **监听器**：获取所有注册的 `ChatModelListener` 监听器，并创建一个并发哈希映射 `attributes` 用于存储属性。
- **请求处理**：调用 `onRequest` 方法通知监听器请求即将开始。
- **聊天处理**：调用 `doChat` 方法执行实际的聊天请求，并获取响应。
- **响应处理**：调用 `onResponse` 方法通知监听器响应已接收。
- **错误处理**：如果在 `doChat` 方法中抛出异常，调用 `onError` 方法通知监听器错误发生，并重新抛出异常。

### 3.2  `defaultRequestParameters()` 方法

返回默认的请求参数。默认实现返回一个空的 `ChatRequestParameters` 对象。

### 3.3  `listeners()` 方法

返回所有注册的 `ChatModelListener` 监听器。默认实现返回一个空列表。

### 3.4  `provider()` 方法

返回模型的提供者。默认实现返回 `OTHER`。

### 3.5  `doChat(ChatRequest chatRequest)` 方法

执行实际的聊天请求。默认实现抛出 `RuntimeException`，表示该方法需要由具体实现类重写。

### 3.6  `chat(String userMessage)` 方法

接收一个用户消息字符串，创建一个 `ChatRequest` 对象，并调用 `chat(ChatRequest chatRequest)` 方法获取响应。最后返回 AI 的消息文本。

### 3.7  `chat(ChatMessage... messages)` 方法

接收多个 `ChatMessage` 对象，创建一个 `ChatRequest` 对象，并调用 `chat(ChatRequest chatRequest)` 方法获取响应。

### 3.8  `chat(List<ChatMessage> messages)` 方法

接收一个 `ChatMessage` 列表，创建一个 `ChatRequest` 对象，并调用 `chat(ChatRequest chatRequest)` 方法获取响应。

### 3.9 `supportedCapabilities()` 方法

返回模型支持的能力集合。默认实现返回一个空集合。

### 3.10 接口源码

![](https://gitee.com/itbeien/base/raw/master/images/IChatModel.png)

```java
public interface ChatModel {

    
    default ChatResponse chat(ChatRequest chatRequest) {

        // 创建一个最终的ChatRequest对象，合并默认参数和传入的参数
    // 使用ChatRequest的builder模式创建一个新的ChatRequest对象
    // 将传入的chatRequest的messages和默认请求参数合并
        ChatRequest finalChatRequest = ChatRequest.builder()
                .messages(chatRequest.messages())
                .parameters(defaultRequestParameters().overrideWith(chatRequest.parameters()))
                .build();

        // 获取所有的监听器
    // 调用listeners()方法获取当前所有的ChatModelListener监听器列表
        List<ChatModelListener> listeners = listeners();
        // 创建一个线程安全的属性集合
    // 使用ConcurrentHashMap创建一个线程安全的Map，用于存储请求和响应过程中的属性
        Map<Object, Object> attributes = new ConcurrentHashMap<>();

        // 触发请求事件
    // 调用onRequest方法，通知所有监听器请求即将开始
        onRequest(finalChatRequest, provider(), attributes, listeners);
        try {
            // 执行聊天请求
        // 调用doChat方法执行实际的聊天请求，传入最终的ChatRequest对象
            ChatResponse chatResponse = doChat(finalChatRequest);
            // 触发响应事件
        // 调用onResponse方法，通知所有监听器请求已完成，并传递响应结果
            onResponse(chatResponse, finalChatRequest, provider(), attributes, listeners);
        // 返回聊天响应
            return chatResponse;
        } catch (Exception error) {
            // 触发错误事件
        // 如果在执行聊天请求过程中发生异常，调用onError方法，通知所有监听器发生了错误
            onError(error, finalChatRequest, provider(), attributes, listeners);
        // 抛出异常
        // 将捕获到的异常重新抛出，以便上层代码处理
            throw error;
        }
    }

    /**
     * 返回默认的请求参数
     *
     * @return 默认的请求参数
     */
    default ChatRequestParameters defaultRequestParameters() {
        return ChatRequestParameters.builder().build();
    }

    /**
     * 返回所有的监听器
     *
     * @return 所有的监听器
     */
    default List<ChatModelListener> listeners() {
        return Collections.emptyList();
    }

    /**
     * 返回模型提供者
     *
     * @return 模型提供者
     */
    default ModelProvider provider() {
        return OTHER;
    }

    /**
     * 执行聊天请求
     *
     * @param chatRequest 聊天请求
     * @return 聊天响应
     */
    default ChatResponse doChat(ChatRequest chatRequest) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * 使用用户消息执行聊天请求
     *
     * @param userMessage 用户消息
     * @return AI响应消息的文本
     */
    default String chat(String userMessage) {

        // 创建一个包含用户消息的ChatRequest对象
        ChatRequest chatRequest = ChatRequest.builder()
                .messages(UserMessage.from(userMessage))
                .build();

        // 执行聊天请求并返回AI响应消息的文本
        ChatResponse chatResponse = chat(chatRequest);

        return chatResponse.aiMessage().text();
    }

    /**
     * 使用多个聊天消息执行聊天请求
     *
     * @param messages 聊天消息
     * @return 聊天响应
     */
    default ChatResponse chat(ChatMessage... messages) {

        // 创建一个包含多个聊天消息的ChatRequest对象
        ChatRequest chatRequest = ChatRequest.builder()
                .messages(messages)
                .build();

        return chat(chatRequest);
    }

    /**
     * 使用聊天消息列表执行聊天请求
     *
     * @param messages 聊天消息列表
     * @return 聊天响应
     */
    default ChatResponse chat(List<ChatMessage> messages) {

        // 创建一个包含聊天消息列表的ChatRequest对象
        ChatRequest chatRequest = ChatRequest.builder()
                .messages(messages)
                .build();

        return chat(chatRequest);
    }

    /**
     * 返回支持的能力集合
     *
     * @return 支持的能力集合
     */
    default Set<Capability> supportedCapabilities() {
        return Set.of();
    }

}
```

## 4 OpenAiChatModel源码分析

`OpenAiChatModel`，它实现了`ChatModel`接口。这个类代表了一个具有聊天功能接口的OpenAI语言模型，如gpt-4o-mini和DeepSeek等。

### 4.1 创建OpenAiChatModel时序图

![](https://gitee.com/itbeien/base/raw/master/images/01创建OpenAiChatModel时序图.png)

#### 4.1.1 时序图说明

- **User**：大模型应用程序。
- **Builder**：`OpenAiChatModelBuilder`内部类。
- **Model**：`OpenAiChatModel`类。

1. 用户调用`OpenAiChatModel.builder()`方法创建`OpenAiChatModelBuilder`实例。
2. `OpenAiChatModelBuilder`实例返回给用户。
3. 用户调用`OpenAiChatModelBuilder`实例的方法来设置参数（例如`apiKey`、`modelName`等）。
4. 参数设置完成后，用户调用`Builder`的`build()`方法。
5. `Builder`调用`OpenAiChatModel`的构造函数，将自身作为参数传入。
6. `OpenAiChatModel`构造函数创建`OpenAiChatModel`实例。
7. `OpenAiChatModel`实例返回给`Builder`。
8. `Builder`将`OpenAiChatModel`实例返回给用户。

### 4.1 类的成员变量

- `client`：一个`OpenAiClient`对象，用于与OpenAI API进行通信。
- `maxRetries`：最大重试次数。
- `defaultRequestParameters`：默认的请求参数。
- `responseFormat`：响应格式。
- `supportedCapabilities`：支持的特性集合。
- `strictJsonSchema`：是否严格遵循JSON模式。
- `strictTools`：是否严格使用工具。
- `listeners`：聊天模型监听器列表。

### 4.2 构造函数

​	构造函数使用一个`OpenAiChatModelBuilder`对象来初始化成员变量。它还包含了一些逻辑来处理特定的API密钥和基础URL，以及设置默认请求参数和OpenAI特定的请求参数。

### 4.3 方法

- `defaultRequestParameters()`：返回默认的请求参数。
- `supportedCapabilities()`：返回支持的特性集合，如果响应格式为"json_schema"，则添加`RESPONSE_FORMAT_JSON_SCHEMA`特性。
- `doChat(ChatRequest chatRequest)`：执行聊天请求，并返回聊天响应。它首先验证请求参数，然后将请求转换为OpenAI格式的请求，并通过客户端发送请求。最后，它将OpenAI的响应转换为聊天响应。
- `listeners()`：返回聊天模型监听器列表。
- `provider()`：返回模型提供者，这里是`OPEN_AI`。
- `builder()`：返回一个`OpenAiChatModelBuilder`对象，用于构建`OpenAiChatModel`实例。

### 4.4 内部类

- `OpenAiChatModelBuilder`：用于构建`OpenAiChatModel`实例。它包含了许多设置方法，用于设置各种参数和属性。该类采用建造者设计模式和链式调用创建对象和设置属性参数。

### 4.5 类源码

![](https://gitee.com/itbeien/base/raw/master/images/OpenAiChatModel.png)

```java
public class OpenAiChatModel implements ChatModel {

    // OpenAI客户端实例
    private final OpenAiClient client;
    // 最大重试次数
    private final Integer maxRetries;

    // 默认请求参数
    private final OpenAiChatRequestParameters defaultRequestParameters;
    // 响应格式
    private final String responseFormat;
    // 支持的能力集合
    private final Set<Capability> supportedCapabilities;
    // 是否严格遵循JSON Schema
    private final Boolean strictJsonSchema;
    // 是否严格使用工具
    private final Boolean strictTools;

    // 监听器列表
    private final List<ChatModelListener> listeners;

    // 构造函数，使用Builder模式进行初始化
    public OpenAiChatModel(OpenAiChatModelBuilder builder) {

        // 检查API密钥和基础URL是否匹配
        if ("demo".equals(builder.apiKey) && !"http://langchain4j.dev/demo/openai/v1".equals(builder.baseUrl)) {
            // TODO remove before releasing 1.0.0
            throw new RuntimeException("""
                    If you wish to continue using the 'demo' key, please specify the base URL explicitly:
                    OpenAiChatModel.builder().baseUrl("http://langchain4j.dev/demo/openai/v1").apiKey("demo").build();
                    """);
        }

        // 初始化OpenAI客户端
        this.client = OpenAiClient.builder()
                .httpClientBuilder(builder.httpClientBuilder)
                .baseUrl(getOrDefault(builder.baseUrl, DEFAULT_OPENAI_URL))
                .apiKey(builder.apiKey)
                .organizationId(builder.organizationId)
                .projectId(builder.projectId)
                .connectTimeout(getOrDefault(builder.timeout, ofSeconds(15)))
                .readTimeout(getOrDefault(builder.timeout, ofSeconds(60)))
                .logRequests(getOrDefault(builder.logRequests, false))
                .logResponses(getOrDefault(builder.logResponses, false))
                .userAgent(DEFAULT_USER_AGENT)
                .customHeaders(builder.customHeaders)
                .build();
        this.maxRetries = getOrDefault(builder.maxRetries, 3);

        // 初始化通用请求参数
        ChatRequestParameters commonParameters;
        if (builder.defaultRequestParameters != null) {
            commonParameters = builder.defaultRequestParameters;
        } else {
            commonParameters = DefaultChatRequestParameters.builder().build();
        }

        // 初始化OpenAI特定请求参数
        OpenAiChatRequestParameters openAiParameters;
        if (builder.defaultRequestParameters instanceof OpenAiChatRequestParameters openAiChatRequestParameters) {
            openAiParameters = openAiChatRequestParameters;
        } else {
            openAiParameters = OpenAiChatRequestParameters.builder().build();
        }

        // 合并通用和OpenAI特定请求参数
        this.defaultRequestParameters = OpenAiChatRequestParameters.builder()
                // common parameters
                .modelName(getOrDefault(builder.modelName, commonParameters.modelName()))
                .temperature(getOrDefault(builder.temperature, commonParameters.temperature()))
                .topP(getOrDefault(builder.topP, commonParameters.topP()))
                .frequencyPenalty(getOrDefault(builder.frequencyPenalty, commonParameters.frequencyPenalty()))
                .presencePenalty(getOrDefault(builder.presencePenalty, commonParameters.presencePenalty()))
                .maxOutputTokens(getOrDefault(builder.maxTokens, commonParameters.maxOutputTokens()))
                .stopSequences(getOrDefault(builder.stop, () -> copyIfNotNull(commonParameters.stopSequences())))
                .toolSpecifications(copyIfNotNull(commonParameters.toolSpecifications()))
                .toolChoice(commonParameters.toolChoice())
                .responseFormat(getOrDefault(fromOpenAiResponseFormat(builder.responseFormat), commonParameters.responseFormat()))
                // OpenAI-specific parameters
                .maxCompletionTokens(getOrDefault(builder.maxCompletionTokens, openAiParameters.maxCompletionTokens()))
                .logitBias(getOrDefault(builder.logitBias, () -> copyIfNotNull(openAiParameters.logitBias())))
                .parallelToolCalls(getOrDefault(builder.parallelToolCalls, openAiParameters.parallelToolCalls()))
                .seed(getOrDefault(builder.seed, openAiParameters.seed()))
                .user(getOrDefault(builder.user, openAiParameters.user()))
                .store(getOrDefault(builder.store, openAiParameters.store()))
                .metadata(getOrDefault(builder.metadata, () -> copyIfNotNull(openAiParameters.metadata())))
                .serviceTier(getOrDefault(builder.serviceTier, openAiParameters.serviceTier()))
                .reasoningEffort(openAiParameters.reasoningEffort())
                .build();
        this.responseFormat = builder.responseFormat;
        this.supportedCapabilities = new HashSet<>(getOrDefault(builder.supportedCapabilities, emptySet()));
        this.strictJsonSchema = getOrDefault(builder.strictJsonSchema, false); // TODO move into OpenAI-specific params?
        this.strictTools = getOrDefault(builder.strictTools, false); // TODO move into OpenAI-specific params?
        this.listeners = builder.listeners == null ? emptyList() : new ArrayList<>(builder.listeners);
    }

    @Override
// 标注此方法重写了父类或接口中的方法
    public OpenAiChatRequestParameters defaultRequestParameters() {
    // 定义一个公共方法，返回类型为OpenAiChatRequestParameters，方法名为defaultRequestParameters
        return defaultRequestParameters;
    // 返回类的成员变量defaultRequestParameters的值
    }

    @Override
// 重写父类或接口中的方法
    public Set<Capability> supportedCapabilities() {
    // 返回当前对象支持的能力集合
        Set<Capability> capabilities = new HashSet<>(supportedCapabilities);
    // 创建一个新的HashSet，初始值为当前对象的supportedCapabilities集合
        if ("json_schema".equals(responseFormat)) {
        // 检查responseFormat是否等于"json_schema"
            capabilities.add(RESPONSE_FORMAT_JSON_SCHEMA);
        // 如果是，则向capabilities集合中添加RESPONSE_FORMAT_JSON_SCHEMA能力
        }
        return capabilities;
    // 返回更新后的capabilities集合
    }

    @Override
    public ChatResponse doChat(ChatRequest chatRequest) {

    // 将ChatRequest中的参数转换为OpenAiChatRequestParameters类型
        OpenAiChatRequestParameters parameters = (OpenAiChatRequestParameters) chatRequest.parameters();
    // 验证参数是否合法
        InternalOpenAiHelper.validate(parameters);

    // 将ChatRequest转换为OpenAI的ChatCompletionRequest
        ChatCompletionRequest openAiRequest =
                toOpenAiChatRequest(chatRequest, parameters, strictTools, strictJsonSchema).build();

    // 使用重试机制调用OpenAI的chatCompletion接口，并获取响应
        ChatCompletionResponse openAiResponse = withRetryMappingExceptions(() ->
                client.chatCompletion(openAiRequest).execute(), maxRetries);

    // 构建OpenAiChatResponseMetadata对象，包含响应的元数据信息
        OpenAiChatResponseMetadata responseMetadata = OpenAiChatResponseMetadata.builder()
                .id(openAiResponse.id()) // 设置响应ID
                .modelName(openAiResponse.model()) // 设置模型名称
                .tokenUsage(tokenUsageFrom(openAiResponse.usage())) // 设置令牌使用情况
                .finishReason(finishReasonFrom(openAiResponse.choices().get(0).finishReason())) // 设置完成原因
                .created(openAiResponse.created()) // 设置创建时间
                .serviceTier(openAiResponse.serviceTier()) // 设置服务层级
                .systemFingerprint(openAiResponse.systemFingerprint()) // 设置系统指纹
                .build();

    // 构建并返回ChatResponse对象，包含AI的消息和元数据
        return ChatResponse.builder()
                .aiMessage(aiMessageFrom(openAiResponse)) // 设置AI的消息
                .metadata(responseMetadata) // 设置元数据
                .build();
    }

    @Override
    public List<ChatModelListener> listeners() {
        return listeners;
    }

    @Override
    public ModelProvider provider() {
        return OPEN_AI;
    }

    // 静态方法，用于获取Builder实例
    public static OpenAiChatModelBuilder builder() {
        for (OpenAiChatModelBuilderFactory factory : loadFactories(OpenAiChatModelBuilderFactory.class)) {
            return factory.get();
        }
        return new OpenAiChatModelBuilder();
    }

    // Builder类，用于构建OpenAiChatModel实例
    public static class OpenAiChatModelBuilder {

    // HttpClientBuilder实例，用于配置HTTP客户端
        private HttpClientBuilder httpClientBuilder;
    // OpenAI API的基础URL
        private String baseUrl;
    // OpenAI API的密钥
        private String apiKey;
    // 组织ID
        private String organizationId;
    // 项目ID
        private String projectId;

    // 默认的聊天请求参数
        private ChatRequestParameters defaultRequestParameters;
    // 模型名称
        private String modelName;
    // 温度参数，用于控制生成文本的多样性
        private Double temperature;
    // topP参数，用于控制生成文本的多样性
        private Double topP;
    // 停止序列，用于控制生成文本的结束
        private List<String> stop;
    // 最大令牌数
        private Integer maxTokens;
    // 最大完成令牌数
        private Integer maxCompletionTokens;
    // 存在惩罚参数，用于控制生成文本的多样性
        private Double presencePenalty;
    // 频率惩罚参数，用于控制生成文本的多样性
        private Double frequencyPenalty;
    // logit偏置参数，用于控制生成文本的多样性
        private Map<String, Integer> logitBias;
    // 支持的能力集合
        private Set<Capability> supportedCapabilities;
    // 响应格式
        private String responseFormat;
    // 是否严格遵循JSON模式
        private Boolean strictJsonSchema;
    // 随机种子
        private Integer seed;
    // 用户标识
        private String user;
    // 是否严格使用工具
        private Boolean strictTools;
    // 是否并行调用工具
        private Boolean parallelToolCalls;
    // 是否存储结果
        private Boolean store;
    // 元数据
        private Map<String, String> metadata;
    // 服务层级
        private String serviceTier;
    // 超时时间
        private Duration timeout;
    // 最大重试次数
        private Integer maxRetries;
    // 是否记录请求
        private Boolean logRequests;
    // 是否记录响应
        private Boolean logResponses;
    // 自定义HTTP头
        private Map<String, String> customHeaders;
    // 聊天模型监听器列表
        private List<ChatModelListener> listeners;

    // 构造函数，默认为public以便扩展
        public OpenAiChatModelBuilder() {
            // This is public so it can be extended
        }

    // 设置HttpClientBuilder
        public OpenAiChatModelBuilder httpClientBuilder(HttpClientBuilder httpClientBuilder) {
            this.httpClientBuilder = httpClientBuilder;
            return this;
        }

        public OpenAiChatModelBuilder defaultRequestParameters(ChatRequestParameters parameters) {
            this.defaultRequestParameters = parameters;
            return this;
        }

        public OpenAiChatModelBuilder modelName(String modelName) {
            this.modelName = modelName;
            return this;
        }

        public OpenAiChatModelBuilder modelName(OpenAiChatModelName modelName) {
            this.modelName = modelName.toString();
            return this;
        }

        public OpenAiChatModelBuilder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public OpenAiChatModelBuilder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public OpenAiChatModelBuilder organizationId(String organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public OpenAiChatModelBuilder projectId(String projectId) {
            this.projectId = projectId;
            return this;
        }

        public OpenAiChatModelBuilder temperature(Double temperature) {
            this.temperature = temperature;
            return this;
        }

        public OpenAiChatModelBuilder topP(Double topP) {
            this.topP = topP;
            return this;
        }

        public OpenAiChatModelBuilder stop(List<String> stop) {
            this.stop = stop;
            return this;
        }

        public OpenAiChatModelBuilder maxTokens(Integer maxTokens) {
            this.maxTokens = maxTokens;
            return this;
        }

        public OpenAiChatModelBuilder maxCompletionTokens(Integer maxCompletionTokens) {
            this.maxCompletionTokens = maxCompletionTokens;
            return this;
        }

        public OpenAiChatModelBuilder presencePenalty(Double presencePenalty) {
            this.presencePenalty = presencePenalty;
            return this;
        }

        public OpenAiChatModelBuilder frequencyPenalty(Double frequencyPenalty) {
            this.frequencyPenalty = frequencyPenalty;
            return this;
        }

        public OpenAiChatModelBuilder logitBias(Map<String, Integer> logitBias) {
            this.logitBias = logitBias;
            return this;
        }

        public OpenAiChatModelBuilder responseFormat(String responseFormat) {
            this.responseFormat = responseFormat;
            return this;
        }

        public OpenAiChatModelBuilder supportedCapabilities(Set<Capability> supportedCapabilities) {
            this.supportedCapabilities = supportedCapabilities;
            return this;
        }

        public OpenAiChatModelBuilder supportedCapabilities(Capability... supportedCapabilities) {
            return supportedCapabilities(new HashSet<>(asList(supportedCapabilities)));
        }

        public OpenAiChatModelBuilder strictJsonSchema(Boolean strictJsonSchema) {
            this.strictJsonSchema = strictJsonSchema;
            return this;
        }

        public OpenAiChatModelBuilder seed(Integer seed) {
            this.seed = seed;
            return this;
        }

        public OpenAiChatModelBuilder user(String user) {
            this.user = user;
            return this;
        }

        public OpenAiChatModelBuilder strictTools(Boolean strictTools) {
            this.strictTools = strictTools;
            return this;
        }

        public OpenAiChatModelBuilder parallelToolCalls(Boolean parallelToolCalls) {
            this.parallelToolCalls = parallelToolCalls;
            return this;
        }

        public OpenAiChatModelBuilder store(Boolean store) {
            this.store = store;
            return this;
        }

        public OpenAiChatModelBuilder metadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        public OpenAiChatModelBuilder serviceTier(String serviceTier) {
            this.serviceTier = serviceTier;
            return this;
        }

        public OpenAiChatModelBuilder timeout(Duration timeout) {
            this.timeout = timeout;
            return this;
        }

        public OpenAiChatModelBuilder maxRetries(Integer maxRetries) {
            this.maxRetries = maxRetries;
            return this;
        }

        public OpenAiChatModelBuilder logRequests(Boolean logRequests) {
            this.logRequests = logRequests;
            return this;
        }

        public OpenAiChatModelBuilder logResponses(Boolean logResponses) {
            this.logResponses = logResponses;
            return this;
        }

        public OpenAiChatModelBuilder customHeaders(Map<String, String> customHeaders) {
            this.customHeaders = customHeaders;
            return this;
        }

        public OpenAiChatModelBuilder listeners(List<ChatModelListener> listeners) {
            this.listeners = listeners;
            return this;
        }

        // 构建OpenAiChatModel实例
        public OpenAiChatModel build() {
            return new OpenAiChatModel(this);
        }
    }
}
```

## 5 最佳实践

```java
package cn.itbeien.langchain4j;

import dev.langchain4j.model.openai.OpenAiChatModel;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI/支付系统/SAAS多租户基础技术平台学习社群
 * langchain4j-core模块，定义核心抽象（如ChatModel和EmbeddingStore）及其api
 * Copyright© 2025 itbeien
 */
public class DeepSeekExamples {
    static class Simple_Prompt{
        public static void main(String[] args) {
            OpenAiChatModel chatModel = OpenAiChatModel.builder()
                   .baseUrl(ApiContants.DEEPSEEK_API_URL)
                   .apiKey(ApiContants.DEEPSEEK_API_KEY)
                   .modelName(ApiContants.DEEPSEEK_API_MODEL)
                   .build();
            String joke = chatModel.chat("你是谁");

            System.out.println(joke);
        }
    }
}
```

## 6 源码地址

**贝恩聊架构探索编程-程序员导航文章、资料和源代码会同步到以下地址，代码和资料每周都会同步更新**

```markdown
https://gitee.com/itbeien/programmer-guide

https://github.com/itbeien/programmer-guide
```

![](https://gitee.com/itbeien/base/raw/master/images/github-code.png)

