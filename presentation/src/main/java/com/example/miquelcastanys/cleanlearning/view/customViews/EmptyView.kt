/*            _MMMMM`
 *     __MMMMMMMMM`       J        openTrends Solucions i Sistemes, S.L.
 * JMMMMMMMMMMMMF       JM         http://www.opentrends.net
 * MMMMMMMMMMF       _JMM`         info@opentrends.net
 * MMMMMMMF`    .JMMMMF`
 * """")    _JMMMMMMF`             Llacuna, 166 Planta 10
 * _MMMMMMMMMMMMMMM`      .M)      Barcelona, 08018
 * MMMMMMMMMMMMMF`     .JMM`       Spain
 * MMMMMMMMMM"     _MMMMMF
 * M4MMM""`   ._MMMMMMMM`          *************************************
 * _______MMMMMMMMMMMF             most-popular-movies
 * MMMMMMMMMMMMMMMM"               *************************************
 * MMMMMMMMMMMMF"                  Copyright (C) 2018 openTrends, Tots els drets reservats
 * MMMMMMMM""                      Copyright (C) 2018 openTrends, All Rights Reserved
 *
 *                                 This program is free software; you can redistribute it and/or modify
 *                                 it under the terms of the GNU General Public License as published by
 *                                 the Free Software Foundation; either version 2 of the License, or
 *                                 (at your option) any later version.
 *                             
 *                                 This program is distributed in the hope that it will be useful,
 *                                 but WITHOUT ANY WARRANTY; without even the implied warranty of
 *                                 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *                                 GNU General Public License for more details.
 *                             
 *                                 You should have received a copy of the GNU General Public License along
 *                                 with this program; if not, write to the Free Software Foundation, Inc.,
 *                                 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. 
 */

package com.example.miquelcastanys.cleanlearning.view.customViews

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.miquelcastanys.cleanlearning.R
import com.example.miquelcastanys.cleanlearning.entities.enumerations.EmptyViewEnumeration
import kotlinx.android.synthetic.main.generic_empty_view.view.*

class EmptyView : FrameLayout {
    private var imageView: ImageView? = null
    private var titleTv: TextView? = null
    private var textTv: TextView? = null
    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    private fun init() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.generic_empty_view, this, true)
        imageView = view.genericEmptyViewIv
        titleTv = view.genericEmptyViewTitleTv
        textTv = view.genericEmptyViewTextTv
    }
    fun fillViews(emptyViewEnumeration: EmptyViewEnumeration) {
        imageView!!.setImageDrawable(ContextCompat.getDrawable(context, emptyViewEnumeration.imageId))
        titleTv!!.text = context.getString(emptyViewEnumeration.title)
        textTv!!.text = context.getString(emptyViewEnumeration.subtitle)
    }
}