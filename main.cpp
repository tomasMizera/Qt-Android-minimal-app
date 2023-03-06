
#include <qglobal.h>
#if QT_VERSION >= 0x050000
#include <QGuiApplication>
#include <QQmlApplicationEngine>
#else
#endif

#include "androidinterface.h"

int main(int argc, char *argv[])
{
  QGuiApplication app(argc, argv);

  QQmlApplicationEngine engine;

  const QUrl url(u"qrc:/dummy-qt-android-app/main.qml"_qs);

  QObject::connect(&engine, &QQmlApplicationEngine::objectCreated,
                   &app, [url](QObject *obj, const QUrl &objUrl) {
    if (!obj && url == objUrl)
      QCoreApplication::exit(-1);
  }, Qt::QueuedConnection);

  qmlRegisterType<AndroidInterface>("my_lib", 1, 0, "AndroidInterface");

  engine.load(url);

  return app.exec();
}
