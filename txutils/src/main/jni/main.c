// Header
#include "com_tianxiabuyi_txutils_util_KeyUtils.h"
// Method
JNIEXPORT jstring JNICALL Java_com_tianxiabuyi_txutils_util_KeyUtils_something (JNIEnv *env, jobject obj) {
    return (*env)->NewStringUTF(env, "eeesys");
}
