#include <stdio.h>
#include <jni.h>
#include <stdlib.h>
#include <stdint.h>
#include "TeaCryptoManager.h"

/*
 * Class:     TeaCryptoManager
 * Method:    NativeEncrypt
 * Signature: ([I[I)V
 */
JNIEXPORT void JNICALL Java_TeaCryptoManager_NativeEncrypt(JNIEnv *env, jobject thisObj, jintArray values, jintArray keys)
{
	jint *v = (jint *)(*env)->GetIntArrayElements(values, NULL);
	jint *k = (jint *)(*env)->GetIntArrayElements(keys, NULL);

	uint32_t v0=(uint32_t)v[0], v1=(uint32_t)v[1], sum=0, i;           /* set up */
    uint32_t delta=0x9e3779b9;                     /* a key schedule constant */
    uint32_t k0=(uint32_t)k[0], k1=(uint32_t)k[1], k2=(uint32_t)k[2], k3=(uint32_t)k[3];   /* cache key */
    for (i=0; i < 32; i++) {                       /* basic cycle start */
        sum += delta;
        v0 += ((v1<<4) + k0) ^ (v1 + sum) ^ ((v1>>5) + k1);
        v1 += ((v0<<4) + k2) ^ (v0 + sum) ^ ((v0>>5) + k3);  
    }                                              /* end cycle */
    v[0]=v0; v[1]=v1;
}

/*
 * Class:     TeaCryptoManager
 * Method:    NativeDecrypt
 * Signature: ([I[I)V
 */
JNIEXPORT void JNICALL Java_TeaCryptoManager_NativeDecrypt(JNIEnv *env, jobject thisObj, jintArray values, jintArray keys)
{

}