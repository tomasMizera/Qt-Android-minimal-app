/***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/

#ifndef JNIMESSENGER_H
#define JNIMESSENGER_H

#include <QObject>
#include <qglobal.h>

class JniMessenger : public QObject
{
    Q_OBJECT

public:
    explicit JniMessenger(QObject *parent = nullptr);
    static JniMessenger *instance() { return m_instance; }
//    Q_INVOKABLE void printFromJava(const QString &message);

signals:
    void messageFromJava(const QString &message);

public slots:

private:
    static JniMessenger *m_instance;
};

#endif // JNIMESSENGER_H
