
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
