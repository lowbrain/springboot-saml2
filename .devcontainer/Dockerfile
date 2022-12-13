ARG VERSION_TAG=latest
FROM mcr.microsoft.com/vscode/devcontainers/java:${VERSION_TAG}

# OS Setting
RUN \
    # Locale setting for JPN
    apt-get update \
    && apt-get install -y task-japanese \
    && sed -i -E 's/# (ja_JP.UTF-8)/\1/' /etc/locale.gen \
    && locale-gen ja_JP.UTF-8

# Development tools
ARG GRADLE_VERSION=""
ARG SPRINGBOOT_VERSION=""
RUN \
    echo ". /usr/local/sdkman/bin/sdkman-init.sh; sdk install gradle ${GRADLE_VERSION}" | bash \
    && echo ". /usr/local/sdkman/bin/sdkman-init.sh; sdk install springboot ${SPRINGBOOT_VERSION}" | bash