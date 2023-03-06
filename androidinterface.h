
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

  signals:

};

#endif // ANDROIDINTERFACE_H
