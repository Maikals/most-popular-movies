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

package com.example.miquelcastanys.cleanlearning.view.mostPopularMovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.miquelcastanys.cleanlearning.R
import com.example.miquelcastanys.cleanlearning.view.base.BaseFragment


class MostPopularMoviesFragment : BaseFragment() {

    companion object {
        const val TAG = "MostPopularMoviesFragment"
        fun newInstance() : MostPopularMoviesFragment = MostPopularMoviesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_most_popular_movies, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}