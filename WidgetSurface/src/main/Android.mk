LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

LOCAL_SRC_FILES := $(call all-java-files-under, java)
LOCAL_SRC_FILES += $(call all-Iaidl-files-under, java)

LOCAL_PACKAGE_NAME := WidgetsSurface

include $(BUILD_PACKAGE)
include $(CLEAR_VARS)
