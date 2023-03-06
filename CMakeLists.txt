cmake_minimum_required(VERSION 3.16)

project(dummy-qt-android-app VERSION 0.1 LANGUAGES CXX)

set(CMAKE_AUTOMOC ON)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

find_package(Qt6 6.2 COMPONENTS Quick REQUIRED)

qt_add_executable(appdummy-qt-android-app
    main.cpp
)

qt_add_qml_module(appdummy-qt-android-app
    URI dummy-qt-android-app
    VERSION 1.0
    QML_FILES main.qml 
)

set_target_properties(appdummy-qt-android-app PROPERTIES
    MACOSX_BUNDLE_GUI_IDENTIFIER my.example.com
    MACOSX_BUNDLE_BUNDLE_VERSION ${PROJECT_VERSION}
    MACOSX_BUNDLE_SHORT_VERSION_STRING ${PROJECT_VERSION_MAJOR}.${PROJECT_VERSION_MINOR}
    MACOSX_BUNDLE TRUE
    WIN32_EXECUTABLE TRUE
)

if (ANDROID)
  message(STATUS "this is android build")
  message (${CMAKE_CURRENT_SOURCE_DIR})
  if (EXISTS ${CMAKE_CURRENT_SOURCE_DIR}/android/AndroidManifest.xml)
    message(STATUS "manifest exists!")
    set_target_properties(
      appdummy-qt-android-app
      PROPERTIES QT_ANDROID_PACKAGE_SOURCE_DIR ${CMAKE_CURRENT_SOURCE_DIR}/android
                 QT_ANDROID_TARGET_SDK_VERSION 31
                 QT_ANDROID_MIN_SDK_VERSION 24
                 QT_ANDROID_VERSION_CODE 10
                 QT_ANDROID_VERSION_NAME ${PROJECT_VERSION_MAJOR}.${PROJECT_VERSION_MINOR}.${PROJECT_VERSION_PATCH}
    )
  endif()
endif ()

target_link_libraries(appdummy-qt-android-app
    PRIVATE Qt6::Quick)

install(TARGETS appdummy-qt-android-app
    BUNDLE DESTINATION .
    LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR})
