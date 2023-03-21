
#include "androidinterface.h"

#include <QtCore/private/qandroidextras_p.h>

AndroidInterface::AndroidInterface(QObject *parent)
  : QObject{parent}
{

}

void AndroidInterface::callJavaService() const
{
  // 1. get context object
  auto activity = QJniObject(QNativeInterface::QAndroidApplication::context());

  // 2. create intent with the service
  QAndroidIntent serviceIntent(activity.object(), "com/example/DemoService");

  // 3. build service with the intent
  QJniObject result = activity.callObjectMethod(
        "startService",
        "(Landroid/content/Intent;)Landroid/content/ComponentName;",
        serviceIntent.handle().object());
}

void AndroidInterface::callTrackingService() const
{
  // 1. get context object
  auto activity = QJniObject(QNativeInterface::QAndroidApplication::context());

  // 2. create intent with the service
  QAndroidIntent serviceIntent(activity.object(), "com/example/LocationService");

  // 3. build service with the intent
  QJniObject result = activity.callObjectMethod(
        "startService",
        "(Landroid/content/Intent;)Landroid/content/ComponentName;",
        serviceIntent.handle().object());

  // 4. start listening on broadcast
  QJniEnvironment env;
  jclass javaClass = env.findClass("com/example/BroadcastMiddleware");
  QJniObject classObject(javaClass);

  classObject.callMethod<void>("registerServiceBroadcastReceiver",
                               "(Landroid/content/Context;)V",
                               QNativeInterface::QAndroidApplication::context());
}

void AndroidInterface::callPermissionsActivity() const
{
  QJniObject context = QNativeInterface::QAndroidApplication::context();

  QAndroidIntent activityIntent( context.object(), "com/example/PermissionsActivity" );

  QtAndroidPrivate::startActivity( activityIntent.handle().object(), 100 );
}
