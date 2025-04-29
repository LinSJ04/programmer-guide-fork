# Python语言基础

⚡开发环境

<div align="left">
    <img src="https://img.shields.io/badge/python-3.13.3-%23437291?logo=openjdk&logoColor=%23437291"/>
    <img src="https://img.shields.io/badge/PyCharm-2025.1-%23437291?logo=idea%20Maven&logoColor=%23C71A36&color=%23C71A36"/>
</div>

## 1 关键字

在 Python 里，关键字属于被语言保留的特殊单词，有着特定的用途和含义，你不能把它们当作普通的变量名、函数名或者其他标识符来使用。

| 关键字   | 含义                                                         |
| -------- | ------------------------------------------------------------ |
| False    | 布尔类型的值，表示假。在条件判断等场景中作为否定的条件。例如在 `if False:` 语句块不会被执行。 |
| None     | 表示空值或缺失值，是 `NoneType` 类型的唯一值。常用于函数没有明确返回值时的默认返回，如 `def func(): pass` ，调用 `func()` 会返回 `None`。 |
| True     | 布尔类型的值，表示真。在条件判断等场景中作为肯定的条件。例如在 `if True:` 语句块会被执行。 |
| and      | 逻辑与运算符，用于连接两个布尔表达式，只有当两个表达式都为真时，整个表达式才为真。例如 `(1 < 2) and (3 > 1)` 结果为 `True`。 |
| as       | 在导入模块或异常处理时用于给对象指定别名。例如 `import pandas as pd` ，这里 `pd` 就是 `pandas` 模块的别名；在异常处理 `try: ... except ValueError as e:` 中，`e` 是捕获到的 `ValueError` 异常对象的别名。 |
| assert   | 用于调试，断言某个条件为真，如果条件为假，则会抛出 `AssertionError` 异常。例如 `assert 1 + 1 == 2` 不会有问题，但 `assert 1 + 1 == 3` 会抛出异常。 |
| async    | 用于定义异步函数或异步生成器。使用 `async def` 定义的函数是异步函数，它可以包含 `await` 表达式。例如 `async def async_func(): ...` 。 |
| await    | 只能在异步函数中使用，用于等待一个可等待对象（如协程、任务或 Future）完成。例如 `result = await some_async_function()` 。 |
| break    | 用于跳出循环（`for` 或 `while` 循环）。当执行到 `break` 语句时，循环会立即终止。例如在 `for i in range(10): if i == 5: break` ，当 `i` 等于 5 时，循环就会停止。 |
| class    | 用于定义类，是面向对象编程的基础。例如 `class MyClass: ...` 定义了一个名为 `MyClass` 的类。 |
| continue | 用于跳过当前循环的剩余部分，直接开始下一次循环。例如在 `for i in range(5): if i == 2: continue; print(i)` ，当 `i` 等于 2 时，`print(i)` 语句会被跳过。 |
| def      | 用于定义函数。例如 `def add(a, b): return a + b` 定义了一个名为 `add` 的函数，用于计算两个数的和。 |
| del      | 用于删除对象的引用。例如 `x = [1, 2, 3]; del x` 会删除变量 `x` 对列表 `[1, 2, 3]` 的引用；也可以用于删除列表中的元素，如 `del x[0]` 会删除列表 `x` 的第一个元素。 |
| elif     | 在条件语句（`if-elif-else`）中使用，是 `else if` 的缩写，用于添加额外的条件判断。例如 `if x > 10: ... elif x > 5: ...` 。 |
| else     | 在条件语句（`if-else` 或 `if-elif-else`）中使用，当所有前面的条件都不满足时执行 `else` 语句块；也可用于 `for` 和 `while` 循环，当循环正常结束（没有被 `break` 中断）时执行 `else` 语句块。 |
| except   | 在异常处理中使用，用于捕获和处理异常。例如 `try: ... except ValueError: ...` 表示捕获 `ValueError` 异常并进行相应处理。 |
| finally  | 在异常处理中使用，无论是否发生异常，`finally` 语句块中的代码都会被执行。例如 `try: ... except: ... finally: ...` 。 |
| for      | 用于创建 `for` 循环，可遍历序列（如列表、元组、字符串等）或可迭代对象。例如 `for i in [1, 2, 3]: print(i)` 。 |
| from     | 在导入模块时使用，用于从模块中导入特定的对象。例如 `from math import sqrt` ，只导入 `math` 模块中的 `sqrt` 函数。 |
| global   | 用于在函数内部声明一个全局变量。在函数内部如果要修改全局变量的值，需要使用 `global` 关键字声明。例如 `x = 1; def func(): global x; x = 2` 。 |
| if       | 用于创建条件语句，根据条件的真假来决定是否执行特定的代码块。例如 `if x > 0: ...` 。 |
| import   | 用于导入模块。例如 `import math` 导入 `math` 模块，之后就可以使用该模块中的函数和变量，如 `math.sqrt(4)` 。 |
| in       | 成员运算符，用于判断一个值是否在序列、集合或映射中。例如 `3 in [1, 2, 3]` 结果为 `True` 。 |
| is       | 身份运算符，用于判断两个对象是否是同一个对象（即是否具有相同的内存地址）。例如 `a = [1, 2]; b = a; a is b` 结果为 `True` 。 |
| lambda   | 用于创建匿名函数，通常用于简单的、一次性的函数。例如 `add = lambda a, b: a + b` ，可以像普通函数一样调用 `add(1, 2)` 。 |
| nonlocal | 用于在嵌套函数中引用并修改外层（非全局）函数的变量。例如 `def outer(): x = 1; def inner(): nonlocal x; x = 2` 。 |
| not      | 逻辑非运算符，用于对布尔值取反。例如 `not True` 结果为 `False` 。 |
| or       | 逻辑或运算符，用于连接两个布尔表达式，只要有一个表达式为真，整个表达式就为真。例如 `(1 < 0) or (3 > 1)` 结果为 `True` 。 |
| pass     | 空语句，不做任何事情，通常用于占位，例如在定义类或函数时，如果暂时不想实现具体内容，可以使用 `pass` 。例如 `def func(): pass` 。 |
| raise    | 用于手动抛出异常。例如 `raise ValueError("Invalid input")` 会抛出一个 `ValueError` 异常并附带错误信息。 |
| return   | 用于从函数中返回值，函数执行到 `return` 语句时会立即停止并返回指定的值。例如 `def add(a, b): return a + b` 。 |
| try      | 在异常处理中使用，用于包裹可能会抛出异常的代码块。例如 `try: ... except: ...` 。 |
| while    | 用于创建 `while` 循环，只要条件为真，就会不断执行循环体中的代码。例如 `while x < 10: ...` 。 |
| with     | 用于简化资源管理，会自动处理资源的获取和释放。例如 `with open('file.txt', 'r') as f: ...` ，文件操作结束后会自动关闭文件。 |
| yield    | 用于定义生成器函数，生成器函数在每次调用 `next()` 时会执行到 `yield` 语句并返回一个值，然后暂停执行，下次调用 `next()` 时会从暂停的地方继续执行。例如 `def my_generator(): yield 1; yield 2` 。 |

### 1.1 如何确定有哪些关键字

可以通过以下代码获取Python的所有关键字

```python
import keyword

# 获取所有关键字
all_keywords = keyword.kwlist

# 打印所有关键字
print("Python 3.13的关键字如下：")
for kw in all_keywords:
    print(kw)
```

在Pycharm IDE中运行该代码

![run-code-python](C:\Users\Administrator\Downloads\run-code-python.png)

## 2 变量

​	在 Python 程序中，我们可以**使用变量来保存数据**，**变量有不同的类型**，常用的类型有`int`、`float`、`str`和`bool`。在有需要的情况下，可以通过 Python 内置的函数对变量进行类型转换。变量是可以做运算的，这是解决很多问题的先决条件。**我们可以把变量理解为内存中的一个存储空间，这个空间用于保存数据。**

### 2.1 如何定义变量

```python
"""
这是多行注释，计算机不会执行
@author itbeien
项目网站：https://www.itbeien.cn
公众号：贝恩聊架构
全网同名，欢迎小伙伴们关注
Python/AI/支付系统/SAAS多租户基础技术平台学习社群
Copyright© 2025 itbeien
"""

# 这是单行注释，计算机不会执行

# 定义整数变量
num = 10
# 定义浮点数变量
float_num = 3.14
# 定义字符串变量
string_var = "Hello, World!"
# 定义布尔变量
bool_var = True

# 同时为三个变量赋值
a, b, c = 1, 2, "abc"

# 输出变量值
print(num)
```

## 3 注释

**注释是给人看的，用于描述你的程序或则某个变量名称是什么意思。代码是给计算机执行的，这就是代码和注释的区别**

```python
"""
这是多行注释，计算机不会执行

"""

# 这是单行注释，计算机不会执行
```

