
cmake_minimum_required(VERSION 3.4.1)

file(GLOB files src/main/cpp/src/*.c)

#file(GLOB files RELATIVE ${CMAKE_CURRENT_SOURCE_DIR} "*.*")

add_library(
        native-lib
        SHARED
        src/main/cpp/native-lib.cpp
        ${files})

include_directories(src/main/cpp/include)
include_directories(src/main/cpp/src)

target_link_libraries( # Specifies the target library.
        native-lib

        log)