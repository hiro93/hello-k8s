# 使用`k8s`部署springboot项目

## 新建一个`springboot`项目：

```shell
$ tree
.
├── deployment.yml
├── Dockerfile
├── hello-k8s.iml
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
└── src
    └── main
        ├── java
        │   └── com
        │       └── example
        │           └── hellok8s
        │               ├── HelloController.java
        │               └── HelloK8sApplication.java
        └── resources
            ├── application.properties
            ├── static
            └── templates
```

`Dockerfile`

```dockerfile
FROM openjdk:11

WORKDIR /code
COPY target/hello-k8s-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "hello-k8s-0.0.1-SNAPSHOT.jar"]
```

## 编译发布镜像：

```shell
./mvnw clean package
docker build -t hirosyu/hello-k8s .
docker push hirosyu/hello-k8s
```

YAML文件：

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  labels:
    app: hello-k8s
  name: hello-k8s
spec:
  replicas: 1
  selector:
    matchLabels:
      app: hello-k8s
  strategy: {}
  template:
    metadata:
      labels:
        app: hello-k8s
    spec:
      containers:
      - image: hirosyu/hello-k8s
        name: hello-k8s
        resources: {}
---
apiVersion: v1
kind: Service
metadata:
  labels:
    app: hello-k8s
  name: hello-k8s
spec:
  ports:
  - name: 8080-8080
    port: 8080
    protocol: TCP
    targetPort: 8080
  selector:
    app: hello-k8s
  type: ClusterIP
```

## 创建pod

```shell
kubectl apply -f deployment.yml
# 为了查看效果，我们使用那个kubectl port-forward将端口转发到本地
kubectl port-forward svc/hello-k8s 8080:8080
```

现在打开浏览器 `http://127.0.0.1:8080/version` 可查看到返回结果

> {"hostname":"hello-k8s-7bcdb86dfb-zkspl","version":"v0.1"}
