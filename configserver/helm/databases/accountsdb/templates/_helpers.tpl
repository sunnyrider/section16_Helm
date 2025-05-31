{{/* Define a helper to generate the full MySQL connection URL */}}
{{- define "my-spring-app.fullMySQLConnectionURL" -}}
jdbc:mysql://{{ .Values.mysqlContainerName }}/{{ .Values.mysqlDatabaseName }}?createDatabaseIfNotExist=true&characterEncoding=UTF-8&useUnicode=true&useSSL=false&allowPublicKeyRetrieval=true
{{- end -}}
