Kubernetes manifest configuration for book-store demo project.

manifest files structure tree, mysql and redis manifest are only for demo purpose.
```
root
│  README.md
│  tree.txt
│
├─apps
│  ├─common
│  │      service-db-secret.yaml
│  │      service-limitrange.yaml
│  │      service-role.yaml
│  │      service-rsa-secret.yaml
│  │      service-secret-tls.yaml
│  │
│  ├─order-service
│  │      order-service-configmap.yaml
│  │      order-service-deployment.yaml
│  │      order-service-ingress.yaml
│  │      order-service-service.yaml
│  │
│  └─user-service
│          user-service-configmap.yaml
│          user-service-deployment-new.yaml
│          user-service-deployment.yaml
│          user-service-ingress.yaml
│          user-service-service.yaml
│
├─infra
│  ├─mysql
│  │      mysql-configmap.yaml
│  │      mysql-service.yaml
│  │      mysql-statefulset.yaml
│  │
│  └─redis
│      │  redis-configmap.yaml
│      │  redis-service.yaml
│      │  redis-statefulset.yaml
│      │
│      └─test
│              redis-configmap-acl.yaml
│              redis-configmap.yaml
│              redis-sentinel-service.yaml
│              redis-sentinel-statefulset.yaml
│              redis-service.yaml
│              redis-statefulset.yaml
│
└─test
        busybox.yaml
        dnsutils.yaml
```