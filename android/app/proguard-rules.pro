# Retrofit / OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# Kotlin Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** { *** Companion; }
-keepclasseswithmembers class kotlinx.serialization.json.** { kotlinx.serialization.KSerializer serializer(...); }
-keep,includedescriptorclasses class com.cms.android.**$$serializer { *; }
-keepclassmembers class com.cms.android.** { *** Companion; }
-keepclasseswithmembers class com.cms.android.** { kotlinx.serialization.KSerializer serializer(...); }

# AppAuth
-keep class net.openid.appauth.** { *; }
