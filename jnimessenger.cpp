/***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/

#include "jnimessenger.h"

#include <QJniObject>
#include <QJniEnvironment>
#include <QtCore/private/qandroidextras_p.h>

JniMessenger *JniMessenger::m_instance = nullptr;

static void sendToQt(JNIEnv *env, jobject /*this*/, jstring value)
{
  qDebug() << "Qt printing the data:" << env->GetStringUTFChars( value, nullptr );

  emit JniMessenger::instance()->messageFromJava(env->GetStringUTFChars(value, nullptr));
}

JniMessenger::JniMessenger(QObject *parent) : QObject(parent)
{
    m_instance = this;

    JNINativeMethod methods[] {{"sendToQt", "(Ljava/lang/String;)V", reinterpret_cast<void *>(sendToQt)}};
    QJniObject javaClass("com/example/BroadcastMiddleware");

    QJniEnvironment env;
    jclass objectClass = env->GetObjectClass(javaClass.object<jobject>());

    env->RegisterNatives(objectClass, methods, 1);
    env->DeleteLocalRef(objectClass);
}
