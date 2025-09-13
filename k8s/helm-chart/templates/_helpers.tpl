{{/*
Expand the name of the chart.
*/}}
{{- define "astropa.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "astropa.fullname" -}}
{{- if .Values.fullnameOverride }}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- $name := default .Chart.Name .Values.nameOverride }}
{{- if contains $name .Release.Name }}
{{- .Release.Name | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" }}
{{- end }}
{{- end }}
{{- end }}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "astropa.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Common labels
*/}}
{{- define "astropa.labels" -}}
helm.sh/chart: {{ include "astropa.chart" . }}
{{ include "astropa.selectorLabels" . }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}

{{/*
Selector labels
*/}}
{{- define "astropa.selectorLabels" -}}
app.kubernetes.io/name: {{ include "astropa.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

{{/*
Create the name of the service account to use
*/}}
{{- define "astropa.serviceAccountName" -}}
{{- if .Values.serviceAccount.create }}
{{- default (include "astropa.fullname" .) .Values.serviceAccount.name }}
{{- else }}
{{- default "default" .Values.serviceAccount.name }}
{{- end }}
{{- end }}

{{/*
Backend service name
*/}}
{{- define "astropa.backend.serviceName" -}}
{{- printf "%s-backend" (include "astropa.fullname" .) }}
{{- end }}

{{/*
Frontend service name
*/}}
{{- define "astropa.frontend.serviceName" -}}
{{- printf "%s-frontend" (include "astropa.fullname" .) }}
{{- end }}

{{/*
ConfigMap name
*/}}
{{- define "astropa.configMapName" -}}
{{- printf "%s-config" (include "astropa.fullname" .) }}
{{- end }}

{{/*
Secret name
*/}}
{{- define "astropa.secretName" -}}
{{- printf "%s-secrets" (include "astropa.fullname" .) }}
{{- end }}

{{/*
MySQL service name (when using external MySQL)
*/}}
{{- define "astropa.mysql.serviceName" -}}
{{- if .Values.mysql.enabled }}
{{- printf "%s-mysql" .Release.Name }}
{{- else }}
{{- .Values.externalMysql.host | default "mysql" }}
{{- end }}
{{- end }}

{{/*
Redis service name (when using external Redis)
*/}}
{{- define "astropa.redis.serviceName" -}}
{{- if .Values.redis.enabled }}
{{- printf "%s-redis-master" .Release.Name }}
{{- else }}
{{- .Values.externalRedis.host | default "redis" }}
{{- end }}
{{- end }}

{{/*
Environment-specific domain
*/}}
{{- define "astropa.domain" -}}
{{- if .Values.global.domain }}
{{- .Values.global.domain }}
{{- else }}
{{- printf "%s.%s" .Values.global.environment "example.com" }}
{{- end }}
{{- end }}

{{/*
API URL for frontend
*/}}
{{- define "astropa.apiUrl" -}}
{{- if .Values.frontend.env }}
{{- range .Values.frontend.env }}
{{- if eq .name "API_BASE_URL" }}
{{- .value }}
{{- end }}
{{- end }}
{{- else }}
{{- printf "http://api.%s" (include "astropa.domain" .) }}
{{- end }}
{{- end }}