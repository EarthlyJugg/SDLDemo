
cmake_minimum_required(VERSION 3.4.1)



#file(GLOB files RELATIVE ${CMAKE_CURRENT_SOURCE_DIR} "*.*")

file(GLOB source_file src/main/cpp/*.cpp)
file(GLOB source_file2 src/main/cpp/*.c)

add_library(
        player

        SHARED
        ${source_file}
        ${source_file2})

include_directories(${CMAKE_SOURCE_DIR}/src/main/cpp/includes)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -L${CMAKE_SOURCE_DIR}/src/main/jniLibs/${CMAKE_ANDROID_ARCH_ABI} ")

target_link_libraries( # Specifies the target library.
        player
        turbojpeg
        jnigraphics
        android
        log)