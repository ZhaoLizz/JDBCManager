# JDBCManager使用文档

## 介绍
对sqlserver的jdbc增删改查操作进行的封装。使用了回调和反射机制。使操作数据库更加简便快♂乐。

## 导入
clone项目后,导入`JDBCDao,JDBCHelper,JDBCObject,JDBCSetting`四个类即可

## 初始化
在`JDBCSetting`类中修改常量`URL,USERNAME,PASSWORD`为自己连接**sqlserver数据库**的参数

```java
public class JDBCSetting {
    static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String URL = "jdbc:sqlserver://localhost:1433;DatabaseName=Test";
    static final String USERNAME = "your_username";
    static final String PASSWORD = "your_password";
}

```

## 使用方法
1. 首先创建二维表的映射`JavaBean`类,**类成员变量的名字要和二维表中列名相同**,成员变量类型和二维表中定义的类型一致
2. 创建getter和setter方法
3. 继承自`JDBCObject`

```java

public class Student extends JDBCObject {
    private String student_no;
    private String student_pw;
    private String student_name;
    private String student_target;
    private String student_special;
    private int student_is_manager = 0;     //默认值为0

    public String getStudent_no() {
        return student_no;
    }

    public void setStudent_no(String student_no) {
        this.student_no = student_no;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_pw() {
        return student_pw;
    }

    public void setStudent_pw(String student_pw) {
        this.student_pw = student_pw;
    }

    public String getStudent_target() {
        return student_target;
    }

    public void setStudent_target(String student_target) {
        this.student_target = student_target;
    }

    public String getStudent_special() {
        return student_special;
    }

    public void setStudent_special(String student_special) {
        this.student_special = student_special;
    }

    public int getStudent_is_manager() {
        return student_is_manager;
    }

    public void setStudent_is_manager(int student_is_manager) {
        this.student_is_manager = student_is_manager;
    }

    @Override
    public String toString() {
        return "Student{" +
                "student_no='" + student_no + '\'' +
                ", student_pw='" + student_pw + '\'' +
                ", student_name='" + student_name + '\'' +
                ", student_target='" + student_target + '\'' +
                ", student_special='" + student_special + '\'' +
                ", student_is_manager=" + student_is_manager +
                '}';
    }
}

```

映射参照的关系型数据库如下:

![](http://ww1.sinaimg.cn/mw690/0077h8xtly1fsjpalazrzj31dg047t9d.jpg)

### 添加数据
1. 创建数据库二维表映射的JavaBean对象,set各成员变量要添加的值
2. 调用`save()`方法

```java
    Student student = new Student();
    student.setStudent_no(username);
    student.setStudent_pw(password);
    student.setStudent_target(target);
    student.setStudent_name(studentName);
    student.setStudent_special(special);
    student.save(new JDBCDao.SaveListerner() {
        @Override
        public void onSucceed() {
            //TODO
        }
    
        @Override
        public void onFailed(Exception e) {
            e.printStackTrace();
        }
    });
```

### 删除数据
1. 把删除条件添加进入javabean对象
2. 调用`delete()`方法


```java
//删除名字为Morty,专业为 Science的学生数据
Student student = new Student();
student.setStudent_name("Morty");
student.setStudent_special("Science");
student.delete(new JDBCDao.DeleteListener() {
            @Override
            public void onSucceed() {

            }

            @Override
            public void onFailed(Exception e) {
                e.printStackTrace();
            }
        });
```

### 修改数据
* 修改数据需要两个javabean对象
    * 第一个对象用于设置修改后的值
    * 第二个对象作为查询条件,用于在数据库中找到需要修改的那条数据

```java
        //查找名字为Rick,专业为Science的学生,把他的名字修改为Morty
        Student condition = new Student();
        condition.setStudent_name("Rick");
        condition.setStudent_special("Science");

        Student student = new Student();
        student.setStudent_name("Morty");
        student.update(condition, new JDBCDao.UpdateListener() {
            @Override
            public void onSucceed() {

            }

            @Override
            public void onFailed(Exception e) {
                e.printStackTrace();
            }
        });
```

### 查询数据
* 查询方法会把数据库查询结果映射为`javabean`类的一个`List`对象

1. 为javabean对象设置查询条件
2. 调用`query`方法,传入javabean类的类型信息参数

```java
        //查询名字为 Rick, 专业为Science的学生信息
        Student student = new Student();
        student.setStudent_name("Rick");
        student.setStudent_special("Science");
        student.query(Student.class, new JDBCDao.QueryListener<Student>() {
            @Override
            public void onSucceed(List<Student> result) {
                for (Student s : result) {
                    System.out.println(s);
                }
            }

            @Override
            public void onFailed(Exception e) {
                e.printStackTrace();
            }
        });     

```

## 致读者
鄙人才疏学浅,难免有bug和疏漏。欢迎大家提PullRequest和Issue。项目用于交流，共同学习共同进步。