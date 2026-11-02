package com.hqm.mp

import android.net.Uri

class Image {
    val contentUri: Uri
    val name: String
    val size: Int

    constructor(contentUri: Uri, name: String, size: Int) {
        this.contentUri = contentUri
        this.name = name
        this.size = size
    }

}