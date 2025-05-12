# 1 Spring AI spring-ai-client-chat模块之ChatClient API源码分析

<div align="center">
    <img alt="logo" src="https://gitee.com/itbeien/base/raw/master/images/springai-reading.png" style="height: 80px">
</div>
<div align="center">
    <h2>深入Spring AI，从源码开始</h2>
    <h4>探索Java企业级AI应用框架，理解它的内部机制，带大家从入门到精通</h4>
</div>

<div align="left">
    <img src="https://img.shields.io/badge/Java-17%2B-%23437291?logo=openjdk&logoColor=%23437291"/>
    <img src="https://img.shields.io/badge/SpringAI-1.0.0SNAPSHOT-%23437291?logo=Spring&logoColor=%236DB33F&color=%236DB33F"/>
    <img src="https://img.shields.io/badge/SpringBoot-3.4.5-%23437291?logo=SpringBoot&logoColor=%236DB33F&color=%236DB33F"/>
    <img src="https://img.shields.io/badge/Maven-3.9.9-%23437291?logo=Apache%20Maven&logoColor=%23C71A36&color=%23C71A36"/>
    <img src="https://img.shields.io/badge/IDEA-2025.1-%23437291?logo=idea%20Maven&logoColor=%23C71A36&color=%23C71A36"/>
</div>

![](https://gitee.com/itbeien/base/raw/master/images/ChatClientAPI源码分析-主要内容.png)

## 1 ChatClient基本描述

`ChatClient`，用于与AI模型进行无状态请求，使用流式(Fluent )API，Spring生态 Fluent API 是一种面向对象的API设计模式。该接口提供了一系列静态方法和嵌套接口，用于构建和发送聊天请求，并处理响应。

Spring生态框架中，Fluent API是一种通过方法链式调用提升代码可读性和简洁性的设计模式，其核心是通过返回`this`引用实现链式配置。

Spring Fluent API 的本质是**以建造者模式为逻辑基础，通过链式调用语法实现流畅的接口设计**。这种模式在 Spring 生态中广泛应用于复杂对象的构建、配置管理和外部服务通信，显著提升了开发效率和代码可维护性。

### 1.1 Fluent API的优势

1. **提高代码可读性**：通过方法链，代码逻辑更加清晰，易于理解。
2. **减少样板代码**：通过链式调用，减少了大量的中间变量和方法调用，使代码更加简洁。
3. **增强类型安全**：在编译时期就能发现潜在的错误，提高代码的健壮性

## 2 ChatClient源码分析

### 2.1 主要功能

1. **创建`ChatClient`实例**：通过静态方法`create`和`builder`创建`ChatClient`实例。
2. **发送聊天请求**：通过`prompt`方法发送聊天请求，可以传递字符串内容、`Prompt`对象或直接调用无参数的`prompt`方法。
3. **配置请求**：通过嵌套接口`ChatClientRequestSpec`配置请求，包括设置顾问（advisors）、消息（messages）、选项（options）、工具（tools）和系统/用户提示（system/user prompts）。
4. **处理响应**：通过嵌套接口`CallResponseSpec`和`StreamResponseSpec`处理同步和异步响应。

### 2.2 主要接口和类

- **`ChatClient`接口**：主接口，提供创建客户端实例和处理请求的方法。
- **`Builder`接口**：用于创建和配置`ChatClient`实例的构建器接口。
- **`ChatClientRequestSpec`接口**：用于配置和发送聊天请求的规范接口。
- **`CallResponseSpec`接口**：用于处理同步响应的规范接口。
- **`StreamResponseSpec`接口**：用于处理异步响应的规范接口。
- **`PromptUserSpec`和`PromptSystemSpec`接口**：用于配置用户和系统提示的规范接口。
- **`AdvisorSpec`接口**：用于配置顾问的规范接口。

###  2.3 代码流程

1. 创建`ChatClient`实例：
   - 调用`create`或`builder`方法，传入`ChatModel`、`ObservationRegistry`和可选的`ChatClientObservationConvention`。
   - `create`方法内部调用`builder`方法并构建实例。
2. 配置请求：
   - 调用`prompt`方法获取`ChatClientRequestSpec`实例。
   - 通过`ChatClientRequestSpec`配置请求，包括顾问、消息、选项、工具和提示。
3. 发送请求并处理响应：
   - 调用`call`方法发送同步请求，通过`CallResponseSpec`处理响应。
   - 调用`stream`方法发送异步请求，通过`StreamResponseSpec`处理响应。

### 2.4 代码流程图

![](https://gitee.com/itbeien/base/raw/master/images/ChatClient流程图.png)

### 2.5 接口代码

![](https://gitee.com/itbeien/base/raw/master/images/ChatClient.png)

```java
public interface ChatClient {

    /**
     * 创建一个默认的ChatClient实例，使用默认的ObservationRegistry。
     *
     * @param chatModel 聊天模型
     * @return ChatClient实例
     */
	static ChatClient create(ChatModel chatModel) {
		return create(chatModel, ObservationRegistry.NOOP);
	}

    /**
     * 创建一个ChatClient实例，使用指定的ObservationRegistry。
     *
     * @param chatModel 聊天模型
     * @param observationRegistry 观察注册表
     * @return ChatClient实例
     */
	static ChatClient create(ChatModel chatModel, ObservationRegistry observationRegistry) {
		return create(chatModel, observationRegistry, null);
	}

    /**
     * 创建一个ChatClient实例，使用指定的ObservationRegistry和ObservationConvention。
     *
     * @param chatModel 聊天模型
     * @param observationRegistry 观察注册表
     * @param observationConvention 观察约定
     * @return ChatClient实例
     */
	static ChatClient create(ChatModel chatModel, ObservationRegistry observationRegistry,
			@Nullable ChatClientObservationConvention observationConvention) {
		Assert.notNull(chatModel, "chatModel cannot be null");
		Assert.notNull(observationRegistry, "observationRegistry cannot be null");
		return builder(chatModel, observationRegistry, observationConvention).build();
	}

    /**
     * 创建一个默认的Builder实例，使用默认的ObservationRegistry。
     *
     * @param chatModel 聊天模型
     * @return Builder实例
     */
	static Builder builder(ChatModel chatModel) {
		return builder(chatModel, ObservationRegistry.NOOP, null);
	}

    /**
     * 创建一个Builder实例，使用指定的ObservationRegistry和ObservationConvention。
     *
     * @param chatModel 聊天模型
     * @param observationRegistry 观察注册表
     * @param customObservationConvention 自定义观察约定
     * @return Builder实例
     */
	static Builder builder(ChatModel chatModel, ObservationRegistry observationRegistry,
			@Nullable ChatClientObservationConvention customObservationConvention) {
		Assert.notNull(chatModel, "chatModel cannot be null");
		Assert.notNull(observationRegistry, "observationRegistry cannot be null");
		return new DefaultChatClientBuilder(chatModel, observationRegistry, customObservationConvention);
	}

    /**
     * 创建一个Prompt请求。
     *
     * @return ChatClientRequestSpec实例
     */
	ChatClientRequestSpec prompt();

    /**
     * 创建一个带有文本内容的Prompt请求。
     *
     * @param content 文本内容
     * @return ChatClientRequestSpec实例
     */
	ChatClientRequestSpec prompt(String content);

    /**
     * 创建一个带有Prompt对象的Prompt请求。
     *
     * @param prompt Prompt对象
     * @return ChatClientRequestSpec实例
     */
	ChatClientRequestSpec prompt(Prompt prompt);

	/**
	 * Return a {@link ChatClient.Builder} to create a new {@link ChatClient} whose
	 * settings are replicated from the default {@link ChatClientRequestSpec} of this
	 * client.
	 */
	Builder mutate();

// 定义一个接口 PromptUserSpec，用于规范用户提示信息的构建
	interface PromptUserSpec {

    // 设置文本内容，参数为字符串类型的文本
		PromptUserSpec text(String text);

    // 设置文本内容，参数为资源对象和字符集
		PromptUserSpec text(Resource text, Charset charset);

    // 设置文本内容，参数为资源对象
		PromptUserSpec text(Resource text);

    // 设置参数，参数为键值对映射的Map
		PromptUserSpec params(Map<String, Object> p);

    // 设置单个参数，参数为键和值
		PromptUserSpec param(String k, Object v);

    // 设置媒体内容，参数为多个媒体对象
		PromptUserSpec media(Media... media);

    // 设置媒体内容，参数为媒体类型和URL
		PromptUserSpec media(MimeType mimeType, URL url);

    // 设置媒体内容，参数为媒体类型和资源对象
		PromptUserSpec media(MimeType mimeType, Resource resource);

	}

	/**
	 * Specification for a prompt system.
 * 这是一个用于定义提示系统规范的接口。
	 */
	interface PromptSystemSpec {

    /**
     * 设置提示文本。
     * @param text 提示文本的内容。
     * @return 返回当前PromptSystemSpec实例，以便进行链式调用。
     */
		PromptSystemSpec text(String text);

    /**
     * 设置提示文本，从资源文件中读取，并指定字符集。
     * @param text 提示文本的资源文件。
     * @param charset 读取资源文件时使用的字符集。
     * @return 返回当前PromptSystemSpec实例，以便进行链式调用。
     */
		PromptSystemSpec text(Resource text, Charset charset);

    /**
     * 设置提示文本，从资源文件中读取，使用默认字符集。
     * @param text 提示文本的资源文件。
     * @return 返回当前PromptSystemSpec实例，以便进行链式调用。
     */
		PromptSystemSpec text(Resource text);

    /**
     * 设置多个参数。
     * @param p 包含参数键值对的Map。
     * @return 返回当前PromptSystemSpec实例，以便进行链式调用。
     */
		PromptSystemSpec params(Map<String, Object> p);

    /**
     * 设置单个参数。
     * @param k 参数的键。
     * @param v 参数的值。
     * @return 返回当前PromptSystemSpec实例，以便进行链式调用。
     */
		PromptSystemSpec param(String k, Object v);

	}

// 定义一个名为 AdvisorSpec 的接口
	interface AdvisorSpec {

    // 定义一个方法 param，用于设置单个参数
    // 参数 k 是键，v 是值
    // 返回类型是 AdvisorSpec，允许链式调用
		AdvisorSpec param(String k, Object v);

    // 定义一个方法 params，用于设置多个参数
    // 参数 p 是一个 Map，其中键是 String 类型，值是 Object 类型
    // 返回类型是 AdvisorSpec，允许链式调用
		AdvisorSpec params(Map<String, Object> p);

    // 定义一个方法 advisors，用于设置多个 Advisor 对象
    // 参数 advisors 是一个可变参数，类型是 Advisor
    // 返回类型是 AdvisorSpec，允许链式调用
		AdvisorSpec advisors(Advisor... advisors);

    // 定义一个方法 advisors，用于设置多个 Advisor 对象
    // 参数 advisors 是一个 List，其中元素类型是 Advisor
    // 返回类型是 AdvisorSpec，允许链式调用
		AdvisorSpec advisors(List<Advisor> advisors);

	}

// 定义一个接口 CallResponseSpec，用于规范调用响应的相关操作
	interface CallResponseSpec {

    // 获取实体，类型由 ParameterizedTypeReference 指定，返回值可能为空
		@Nullable
		<T> T entity(ParameterizedTypeReference<T> type);

    // 获取实体，类型由 StructuredOutputConverter 指定，返回值可能为空
		@Nullable
		<T> T entity(StructuredOutputConverter<T> structuredOutputConverter);

    // 获取实体，类型由 Class 指定，返回值可能为空
		@Nullable
		<T> T entity(Class<T> type);

    // 获取 ChatClientResponse 对象
		ChatClientResponse chatClientResponse();

    // 获取 ChatResponse 对象，返回值可能为空
		@Nullable
		ChatResponse chatResponse();

    // 获取响应内容，返回值可能为空
		@Nullable
		String content();

    // 获取 ResponseEntity 对象，类型由 Class 指定
		<T> ResponseEntity<ChatResponse, T> responseEntity(Class<T> type);

    // 获取 ResponseEntity 对象，类型由 ParameterizedTypeReference 指定
		<T> ResponseEntity<ChatResponse, T> responseEntity(ParameterizedTypeReference<T> type);

    // 获取 ResponseEntity 对象，类型由 StructuredOutputConverter 指定
		<T> ResponseEntity<ChatResponse, T> responseEntity(StructuredOutputConverter<T> structuredOutputConverter);

	}

// 定义一个接口 StreamResponseSpec，用于规范流响应的行为
	interface StreamResponseSpec {

    // 方法 chatClientResponse 返回一个 Flux 流，其中包含 ChatClientResponse 类型的数据
    // Flux 是 Reactor 库中的一个类，用于处理 0 到 N 个元素的异步序列
		Flux<ChatClientResponse> chatClientResponse();

    // 方法 chatResponse 返回一个 Flux 流，其中包含 ChatResponse 类型的数据
    // 同样，Flux 用于处理异步序列，这里用于处理聊天响应
		Flux<ChatResponse> chatResponse();

    // 方法 content 返回一个 Flux 流，其中包含 String 类型的数据
    // 这个方法用于处理字符串内容的异步序列
		Flux<String> content();

	}

// 定义一个接口 CallPromptResponseSpec，用于规范调用提示响应的相关方法
	interface CallPromptResponseSpec {

    // 方法 content()，返回一个字符串，表示响应的内容
		String content();

    // 方法 contents()，返回一个字符串列表，表示响应的多项内容
		List<String> contents();

    // 方法 chatResponse()，返回一个 ChatResponse 对象，表示聊天响应
		ChatResponse chatResponse();

	}

// 定义一个接口 StreamPromptResponseSpec，用于规范流式提示响应的行为
	interface StreamPromptResponseSpec {

    // 方法 chatResponse，返回一个 Flux 流，其中包含 ChatResponse 类型的数据
    // Flux 是 Reactor 库中的一个类，表示一个 0 到 N 个元素的异步序列
		Flux<ChatResponse> chatResponse();

    // 方法 content，返回一个 Flux 流，其中包含 String 类型的数据
    // 这个方法可能用于获取与聊天响应相关的文本内容
		Flux<String> content();

	}

	interface ChatClientRequestSpec {

		/**
		 * Return a {@code ChatClient.Builder} to create a new {@code ChatClient} whose
		 * settings are replicated from this {@code ChatClientRequest}.
		 */
		Builder mutate();

/**
 * 该方法用于配置和设置顾问（Advisor）的规格。
 *
 * @param consumer 一个Consumer函数式接口，用于对AdvisorSpec对象进行操作和配置。
 * @return 返回一个ChatClientRequestSpec对象，允许链式调用其他配置方法。
 */
		ChatClientRequestSpec advisors(Consumer<AdvisorSpec> consumer);

/**
 * 定义一个方法，用于设置咨询顾问。
 *
 * @param advisors 一个可变参数，表示多个咨询顾问对象。
 * @return 返回一个ChatClientRequestSpec对象，用于链式调用。
 */
		ChatClientRequestSpec advisors(Advisor... advisors);

		ChatClientRequestSpec advisors(List<Advisor> advisors);

		ChatClientRequestSpec messages(Message... messages);

		ChatClientRequestSpec messages(List<Message> messages);

		<T extends ChatOptions> ChatClientRequestSpec options(T options);

/**
 * 定义一个方法，用于设置聊天客户端请求规范中的工具名称。
 *
 * @param toolNames 一个可变参数，表示一个或多个工具的名称。
 * @return 返回一个ChatClientRequestSpec对象，该对象包含了设置的工具名称。
 */
		ChatClientRequestSpec tools(String... toolNames);

		ChatClientRequestSpec tools(ToolCallback... toolCallbacks);

		ChatClientRequestSpec tools(List<ToolCallback> toolCallbacks);

		ChatClientRequestSpec tools(Object... toolObjects);

		ChatClientRequestSpec tools(ToolCallbackProvider... toolCallbackProviders);

		ChatClientRequestSpec toolContext(Map<String, Object> toolContext);

		ChatClientRequestSpec system(String text);

		ChatClientRequestSpec system(Resource textResource, Charset charset);

		ChatClientRequestSpec system(Resource text);

		ChatClientRequestSpec system(Consumer<PromptSystemSpec> consumer);

		ChatClientRequestSpec user(String text);

		ChatClientRequestSpec user(Resource text, Charset charset);

		ChatClientRequestSpec user(Resource text);

		ChatClientRequestSpec user(Consumer<PromptUserSpec> consumer);

// 定义一个名为 call 的方法，该方法返回一个 CallResponseSpec 类型的对象
		CallResponseSpec call();

// 定义一个名为stream的方法，该方法返回一个StreamResponseSpec类型的对象
		StreamResponseSpec stream();

	}

	/**
	 * A mutable builder for creating a {@link ChatClient}.
 * 这是一个可变的构建器接口，用于创建 {@link ChatClient} 实例。
	 */
	interface Builder {

    /**
     * 设置默认的顾问（Advisor）。
     * @param advisor 可变参数，传入一个或多个 {@link Advisor} 实例。
     * @return 返回当前构建器实例，以便链式调用。
     */
		Builder defaultAdvisors(Advisor... advisor);

    /**
     * 设置默认的顾问（Advisor）。
     * @param advisorSpecConsumer 一个消费者，用于配置 {@link AdvisorSpec}。
     * @return 返回当前构建器实例，以便链式调用。
     */
		Builder defaultAdvisors(Consumer<AdvisorSpec> advisorSpecConsumer);

    /**
     * 设置默认的顾问（Advisor）。
     * @param advisors 一个顾问列表。
     * @return 返回当前构建器实例，以便链式调用。
     */
		Builder defaultAdvisors(List<Advisor> advisors);

    /**
     * 设置默认的聊天选项（ChatOptions）。
     * @param chatOptions 聊天选项实例。
     * @return 返回当前构建器实例，以便链式调用。
     */
		Builder defaultOptions(ChatOptions chatOptions);

    /**
     * 设置默认的用户文本。
     * @param text 默认用户文本。
     * @return 返回当前构建器实例，以便链式调用。
     */
		Builder defaultUser(String text);

    /**
     * 设置默认的用户文本，从资源文件中读取，并指定字符集。
     * @param text 资源文件。
     * @param charset 字符集。
     * @return 返回当前构建器实例，以便链式调用。
     */
		Builder defaultUser(Resource text, Charset charset);

    /**
     * 设置默认的用户文本，从资源文件中读取。
     * @param text 资源文件。
     * @return 返回当前构建器实例，以便链式调用。
     */
		Builder defaultUser(Resource text);

    /**
     * 设置默认的用户文本。
     * @param userSpecConsumer 一个消费者，用于配置 {@link PromptUserSpec}。
     * @return 返回当前构建器实例，以便链式调用。
     */
		Builder defaultUser(Consumer<PromptUserSpec> userSpecConsumer);

    /**
     * 设置默认的系统文本。
     * @param text 默认系统文本。
     * @return 返回当前构建器实例，以便链式调用。
     */
		Builder defaultSystem(String text);

    /**
     * 设置默认的系统文本，从资源文件中读取，并指定字符集。
     * @param text 资源文件。
     * @param charset 字符集。
     * @return 返回当前构建器实例，以便链式调用。
     */
		Builder defaultSystem(Resource text, Charset charset);

    /**
     * 设置默认的系统文本，从资源文件中读取。
     * @param text 资源文件。
     * @return 返回当前构建器实例，以便链式调用。
     */
		Builder defaultSystem(Resource text);

    /**
     * 设置默认的系统文本。
     * @param systemSpecConsumer 一个消费者，用于配置 {@link PromptSystemSpec}。
     * @return 返回当前构建器实例，以便链式调用。
     */
		Builder defaultSystem(Consumer<PromptSystemSpec> systemSpecConsumer);

    /**
     * 设置默认的工具名称。
     * @param toolNames 可变参数，传入一个或多个工具名称。
     * @return 返回当前构建器实例，以便链式调用。
     */
		Builder defaultTools(String... toolNames);

    /**
     * 设置默认的工具回调。
     * @param toolCallbacks 可变参数，传入一个或多个工具回调实例。
     * @return 返回当前构建器实例，以便链式调用。
     */
		Builder defaultTools(ToolCallback... toolCallbacks);

    /**
     * 设置默认的工具回调。
     * @param toolCallbacks 一个工具回调列表。
     * @return 返回当前构建器实例，以便链式调用。
     */
		Builder defaultTools(List<ToolCallback> toolCallbacks);

    /**
     * 设置默认的工具对象。
     * @param toolObjects 可变参数，传入一个或多个工具对象。
     * @return 返回当前构建器实例，以便链式调用。
     */
		Builder defaultTools(Object... toolObjects);

    /**
     * 设置默认的工具回调提供者。
     * @param toolCallbackProviders 可变参数，传入一个或多个工具回调提供者实例。
     * @return 返回当前构建器实例，以便链式调用。
     */
		Builder defaultTools(ToolCallbackProvider... toolCallbackProviders);

    /**
     * 设置默认的工具上下文。
     * @param toolContext 工具上下文映射。
     * @return 返回当前构建器实例，以便链式调用。
     */
		Builder defaultToolContext(Map<String, Object> toolContext);

    /**
     * 克隆当前构建器实例。
     * @return 返回一个新的构建器实例，具有相同的配置。
     */
		Builder clone();

    /**
     * 构建并返回一个 {@link ChatClient} 实例。
     * @return 返回构建的 {@link ChatClient} 实例。
     */
		ChatClient build();

	}

}
```

## 3 ChatClient默认实现源码分析

`DefaultChatClient`，它实现了`ChatClient`接口。这个类的主要功能是处理聊天客户端的请求和响应。

### 3.1 类结构和成员变量

- `DefaultChatClient`类实现了`ChatClient`接口。
- 类中定义了一些静态成员变量和实例成员变量，例如`DEFAULT_CHAT_CLIENT_OBSERVATION_CONVENTION`和`defaultChatClientRequest`。
- `DefaultChatClient`类的构造函数接受一个`DefaultChatClientRequestSpec`对象，并使用`Assert`类来确保输入参数不为null。

### 3.2 核心方法

- `toAdvisedRequest(DefaultChatClientRequestSpec inputRequest)`: 将`DefaultChatClientRequestSpec`对象转换为`AdvisedRequest`对象。这个方法处理用户文本、媒体和消息，并在必要时从消息列表中提取用户文本。
- `prompt()`, `prompt(String content)`, `prompt(Prompt prompt)`: 这些方法用于创建聊天客户端请求规范（`ChatClientRequestSpec`）对象，并允许用户设置请求的内容和选项。
- `mutate()`: 返回一个`Builder`对象，用于创建一个新的`ChatClient`实例，其设置从当前的`ChatClientRequest`复制而来。

### 3.3 内部类

- `DefaultPromptUserSpec`, `DefaultPromptSystemSpec`, `DefaultAdvisorSpec`, `DefaultCallResponseSpec`, `DefaultStreamResponseSpec`, `DefaultChatClientRequestSpec`: 这些内部类分别实现了不同的规范和规格接口，用于处理不同的聊天客户端请求和响应。

### 3.4 代码流程

1. 创建`DefaultChatClient`实例时，需要提供一个`DefaultChatClientRequestSpec`对象。
2. 使用`prompt()`或`prompt(Prompt prompt)`方法创建`ChatClientRequestSpec`对象。
3. 使用`call()`或`stream()`方法从`ChatClientRequestSpec`对象创建`CallResponseSpec`或`StreamResponseSpec`对象。
4. `CallResponseSpec`和`StreamResponseSpec`对象提供了获取聊天响应的方法，如`responseEntity()`, `chatClientResponse()`, `chatResponse()`, `content()`等。

### 3.5 类图

![](https://gitee.com/itbeien/base/raw/master/images/DefaultChatClient.png)

### 3.6 如何创建ChatClient默认实现

![](https://gitee.com/itbeien/base/raw/master/images/创建DefaultChatClient时序图.png)

## 4 最佳实践

```java
package cn.itbeien.springai.test;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI/支付系统/SAAS多租户基础技术平台学习社群
 * Copyright© 2025 itbeien
 */
@SpringBootTest
public class ChatClientTest {
    @Autowired
    private  ChatModel chatModel;
    @Test
    public void test() {
        //编程方式创建ChatClient
        //ChatClient.Builder builder = ChatClient.builder(this.chatModel);
        ChatClient chatClient = ChatClient.create(this.chatModel);
        System.out.println(chatClient.prompt("请你介绍下spring生态中的Fluent API").call().content());
    }

}
```

## 5 源码地址

**贝恩聊架构探索编程-程序员导航文章、资料和源代码会同步到以下地址，代码和资料每周都会同步更新**

```markdown
https://gitee.com/itbeien/programmer-guide

https://github.com/itbeien/programmer-guide
```

![](https://gitee.com/itbeien/base/raw/master/images/github-code.png)