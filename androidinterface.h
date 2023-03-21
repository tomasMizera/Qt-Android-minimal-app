
#ifndef ANDROIDINTERFACE_H
#define ANDROIDINTERFACE_H

#include <QObject>
#include <qglobal.h>

class AndroidInterface : public QObject
{
    Q_OBJECT
  public:
    explicit AndroidInterface(QObject *parent = nullptr);

    Q_INVOKABLE void callJavaService() const;

    Q_INVOKABLE void callTrackingService() const;

    Q_INVOKABLE void callPermissionsActivity() const;

  signals:

};

#endif // ANDROIDINTERFACE_H
