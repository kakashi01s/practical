package com.firefly.practical

import android.net.Uri

data class Posts(var userid:String? ,var postid: String?,var PostImg: String? , var PostCaption : String? ,var PostLikes : Int? ,var PostComments: Int?)
{

    constructor() : this("","","","",0,0) {}
}
