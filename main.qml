import QtQuick
import QtQuick.Controls

import my_lib

Window {
  id: root

  width: 640
  height: 480
  visible: true
  title: qsTr("Minimal Android example app")

  AndroidInterface {
    id: androidInterface
  }

  Item {
    width: root.width
    height: root.height

    Button {
      text: "Run native service"
      anchors.centerIn: parent

      onClicked: {
        androidInterface.callJavaService()
      }
    }
  }
}
