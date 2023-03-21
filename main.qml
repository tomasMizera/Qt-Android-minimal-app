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

    Row {
      anchors.centerIn: parent

      Button {
        text: "Check permissions"

        onClicked: {
          androidInterface.callPermissionsActivity()
        }
      }

      Button {
        text: "Run native service"

        onClicked: {
          androidInterface.callTrackingService()
        }
      }
    }

  }
  Connections {
    target: JniMessenger

    function onMessageFromJava( message ) {
      console.log( "QML printing the data:", message )
    }
  }
}
