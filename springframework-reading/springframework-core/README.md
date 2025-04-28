# 1 Spring核心模块(core)-Resource接口详解

<div align="center">
    <img alt="logo" src="https://gitee.com/itbeien/base/raw/master/images/springframework-reading.png" style="height: 80px">
</div>
<div align="center">
    <h2>精通SpringFramework，从源码开始</h2>
    <h4>探索Java企业级应用框架，理解它的内部原理，从入门到精通</h4>
</div>

<div align="left">
    <img src="https://img.shields.io/badge/Java-17%2B-%23437291?logo=openjdk&logoColor=%23437291"/>
    <img src="https://img.shields.io/badge/SpringFramework-6.2.6-%23437291?logo=Spring&logoColor=%236DB33F&color=%236DB33F"/>
    <img src="https://img.shields.io/badge/SpringBoot-3.4.5-%23437291?logo=SpringBoot&logoColor=%236DB33F&color=%236DB33F"/>
    <img src="https://img.shields.io/badge/Maven-3.9.9-%23437291?logo=Apache%20Maven&logoColor=%23C71A36&color=%23C71A36"/>
    <img src="https://img.shields.io/badge/IDEA-2025.1-%23437291?logo=idea%20Maven&logoColor=%23C71A36&color=%23C71A36"/>
</div>



![](https://gitee.com/itbeien/base/raw/master/images/spring-core-resource-主要内容.png)

## 1 Resource接口详解

Spring的Resource接口位于org.springframework.core.io中，继承InputStreamSource 接口

### 1.1 主要方法及其功能

1. **exists()**：检查资源在物理形式上是否存在。这个方法执行一个明确的存在的检查，而`Resource`句柄的存在仅保证一个有效的描述符句柄。
2. **isReadable()**：指示是否可以通过`getInputStream()`读取此资源的非空内容。默认实现返回`exists()`的结果。
3. **isOpen()**：指示此资源是否表示一个打开的流句柄。如果是`true`，则`InputStream`不能多次读取，并且必须读取并关闭以避免资源泄漏。
4. **isFile()**：指示此资源是否表示文件系统中的一个文件。默认返回`false`。
5. **getURL()**：返回此资源的URL句柄。如果资源不能解析为URL，则抛出`IOException`。
6. **getURI()**：返回此资源的URI句柄。如果资源不能解析为URI，则抛出`IOException`。
7. **getFile()**：返回此资源的文件句柄。如果资源不能解析为绝对文件路径，则抛出`FileNotFoundException`或`IOException`。
8. **readableChannel()**：返回一个`ReadableByteChannel`。默认实现使用`Channels.newChannel(InputStream)`。
9. **getContentAsByteArray()**：返回此资源的字节数组内容。
10. **getContentAsString(Charset charset)**：使用指定的字符集返回此资源的字符串内容。
11. **contentLength()**：确定此资源的内容长度。
12. **lastModified()**：确定此资源的最后修改时间戳。
13. **createRelative(String relativePath)**：创建一个相对于此资源的资源。
14. **getFilename()**：确定此资源的文件名，如果没有文件名则返回`null`。
15. **getDescription()**：返回此资源的描述，用于错误输出。

### 1.2 类图

![](https://gitee.com/itbeien/base/raw/master/images/Resource.png)

### 1.3 源码分析

```java
public interface Resource extends InputStreamSource {

	/**
	 * exists() 方法为 Resource 接口的使用者提供了一种检查资源物理存在性的方式，不同的实现类会根据资源类	  *	型（如文件、网络资源等）采用不同的检查逻辑。
	 */
	boolean exists();

	/**
	 *isReadable() 方法为判断资源是否可读提供了一个默认逻辑，以资源是否存在作为可读的依据。不过实际读取时仍		*可能失败，实现类可以根据具体需求重写该方法。
	 */
	default boolean isReadable() {
		return exists();
	}

	/**
	 * isOpen() 方法为 Resource 接口的使用者提供了判断资源是否为已打开流句柄的能力，默认情况下认为资源不	  *	是已打开流的句柄，实现类可以根据具体的资源类型重写该方法。
	 */
	default boolean isOpen() {
		return false;
	}

	/**
	 * 判断资源是否为文件系统中的文件，默认认为资源不是文件系统里的文件。不同的实现类可依据具体资源类型重	   * 写该方法，给出更准确的判断结果。
	 */
	default boolean isFile() {
		return false;
	}

	/**
	 * getURL() 方法是 Resource 接口提供的一个抽象方法，用于获取资源对应的 URL 句柄。具体的实现逻辑由实		 * 现该接口的类来完成。如果资源无法解析为 URL，则会抛出 IOException 异常。
	 */
	URL getURL() throws IOException;

	/**
	 * getURI() 方法是 Resource 接口提供的抽象方法，其作用是获取资源对应的 URI 句柄。具体实现由实现该接		 *	口的类完成，当资源无法解析为 URI 时会抛出 IOException 异常。
	 */
	URI getURI() throws IOException;

	/**
	 *用于获取资源对应的 File 对象。具体实现由实现该接口的类完成，在资源无法解析为绝对文件路径或出现常规解		 * 析、读取失败时，会抛出相应异常
	 */
	File getFile() throws IOException;

	/**
	 *提供获取资源可读字节通道的能力。默认实现借助输入流创建通道，每次调用会生成新通道。实现类可按需重写该方	  *	法，若资源不存在或通道无法打开，会抛出相应异常。
	 */
	default ReadableByteChannel readableChannel() throws IOException {
		return Channels.newChannel(getInputStream());
	}

	/**
	 *getContentAsByteArray() 方法为 Resource 接口的使用者提供了将资源内容转换为字节数组的能力。默认实		* 现借助输入流和工具类完成读取操作，在资源解析或读取失败时会抛出相应异常。
	 */
	default byte[] getContentAsByteArray() throws IOException {
		return FileCopyUtils.copyToByteArray(getInputStream());
	}

	/**
	 *getContentAsString 方法为 Resource 接口的使用者提供了将资源内容转换为字符串的能力。默认实现借助输	  *	入流、字符流和工具类完成读取和解码操作，在资源解析或读取失败时会抛出相应异常。
	 */
	default String getContentAsString(Charset charset) throws IOException {
		return FileCopyUtils.copyToString(new InputStreamReader(getInputStream(), charset));
	}

	/**
	 * contentLength() 方法是 Resource 接口提供的抽象方法，用于获取资源的内容长度。具体实现由实现该接口	 *	的类完成，当资源无法解析时会抛出 IOException 异常。不同的资源实现类（如 FileSystemResource、		 *	UrlResource 等）会依据自身资源类型，采用不同的方式来确定资源的内容长度。
	 */
	long contentLength() throws IOException;

	/**
	 *lastModified() 方法是 Resource 接口提供的抽象方法，用于获取资源的最后修改时间戳。具体实现由实现该		 *	接口的类完成，当资源无法解析时会抛出 IOException 异常。不同的资源实现类（如 				  	   * FileSystemResource、UrlResource 等）会依据自身资源类型，
	 * 采用不同的方式来获取资源的最后修改时间戳。
	 */
	long lastModified() throws IOException;

	/**
	 * createRelative 方法为 Resource 接口提供了创建相对资源的能力。具体实现由实现该接口的类完成，通过传	 * 入相对路径，可得到相对资源的句柄，若无法确定相对资源则抛出异常。
	 */
	Resource createRelative(String relativePath) throws IOException;

	/**
	 *getFilename() 方法是 Resource 接口提供的抽象方法，用于获取资源的文件名。具体实现由实现该接口的类完		*成，若资源没有文件名则返回 null，并且建议返回未编码的文件名。调用者在使用该方法返回值时，需要考虑 
	 * null值的情况。
	 */
	@Nullable
	String getFilename();

	/**
	 * getDescription() 方法是 Resource 接口提供的抽象方法，用于获取资源的描述信息。具体实现由实现该接口	  *	的类完成，描述信息主要用于错误输出，同时建议实现类在 toString 方法中也返回相同信息。
	 */
	String getDescription();

}

```

## 2 最佳实践

### 2.1 内置资源实现

Spring包括几个内置的资源实现：

- UrlResource
- ClassPathResource
- FileSystemResource
- PathResource
- ServletContextResource
- InputStreamResource
- ByteArrayResource

### 2.2 读取文件代码实例

```java
package cn.itbeien.springframework;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.InputStream;

/**
 * @author itbeien
 * 项目网站：https://www.itbeien.cn
 * 公众号：贝恩聊架构
 * 全网同名，欢迎小伙伴们关注
 * Java/AI/支付系统/SAAS多租户基础技术平台学习社群
 * Copyright© 2025 itbeien
 */
public class ResourceBoot {
    public static void main(String[] args) throws Exception{
        urlResourceTest();
        classPathResourceTest();
        fileSystemResourceTest();
    }

    public static void classPathResourceTest() throws Exception{
        //ClassPathResource是 Spring 框架中的一个组件，用于访问类路径上的资源。
        // 在此示例中，path 变量存储了类路径资源的路径。这里，文件位于 src/main/resources/application.properties 路径目录。
        String path = "application.properties";
        Resource resource = new ClassPathResource(path);
        System.out.println(resource.contentLength());
        System.out.println(resource.getFilename());
        try (InputStream is = resource.getInputStream()) {
            // 读取和处理资源内容
            System.out.println(new String(is.readAllBytes()));
        }
    }

    public static void fileSystemResourceTest() throws Exception{
        //读取文件系统资源 请替换为自己的目录
        String path = "E:\\gitee\\programmer-guide\\springframework-reading\\springframework-core\\README.md";
        Resource resource = new FileSystemResource(path);
        try (InputStream is = resource.getInputStream()) {
            // 读取和处理资源内容
            System.out.println(new String(is.readAllBytes()));
        }
    }

    public static void urlResourceTest() throws Exception{
        //读取网络资源
        Resource resource = new UrlResource("https://baike.baidu.com/item/Java/85979");
        try (InputStream is = resource.getInputStream()) {
            // 读取和处理资源内容
            System.out.println(new String(is.readAllBytes()));
        }
    }
}
```

## 3 源码地址

**贝恩聊架构探索编程-程序员导航文章、资料和源代码会同步到以下地址，代码和资料每周都会同步更新**

```markdown
https://gitee.com/itbeien/programmer-guide

https://github.com/itbeien/programmer-guide
```

![](https://gitee.com/itbeien/base/raw/master/images/github-code.png)