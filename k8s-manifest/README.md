Kubernetes manifest configuration for book-store demo project.

manifest files structure tree, mysql and redis manifest are only for demo purpose.
```
root
│  README.md
│  tree.txt
│  
├─apps
│  ├─common
│  │      service-limitrange.yaml
│  │      service-role.yaml
│  │      service-rsa-secret.yaml
│  │      service-secret-tls.yaml
│  │      
│  ├─user-service
│  │      user-service-configmap.yaml
│  │      user-service-deployment-new.yaml
│  │      user-service-deployment.yaml
│  │      user-service-ingress.yaml
│  │      user-service-secret.yaml
│  │      user-service-service.yaml
│  │      
│  └─user-service-client-test
│          user-service-client-test-configmap.yaml
│          user-service-client-test-deployment.yaml
│          user-service-client-test-ingress.yaml
│          user-service-client-test-service.yaml
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