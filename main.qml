import QtQuick
import QtQuick.Controls

Window {
  id: root

  width: 640
  height: 480
  visible: true
  title: qsTr("Minimal Android example app")

  Item {
    width: root.width
    height: root.height

    Button {
      text: "Run native service"
      anchors.centerIn: parent

      onClicked: {
        console.log("RUUN!")
      }
    }
  }
}
