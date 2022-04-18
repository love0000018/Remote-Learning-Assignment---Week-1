/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.overview

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.marsrealestate.R
import com.example.android.marsrealestate.databinding.FragmentOverviewBinding
import com.example.android.marsrealestate.databinding.GridViewItemBinding
import com.example.android.marsrealestate.detail.DetailFragmentArgs
import com.example.android.marsrealestate.detail.DetailViewModel
import com.example.android.marsrealestate.detail.DetailViewModelFactory
import com.example.android.marsrealestate.network.MarsApiFilter

/**
 * This fragment shows the the status of the Mars real-estate web services transaction.
 */
class OverviewFragment : Fragment() {

    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)

    }

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

//        val binding = FragmentOverviewBinding.inflate(inflater)
        //8-11第十步 Switch to inflating GridViewItemBinding
//        val binding = GridViewItemBinding.inflate(inflater)
        //8-12第四步 Switch to inflating FragmentOverviewBinding
        val application = requireNotNull(activity).application
        val binding = FragmentOverviewBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val marsProperty = DetailFragmentArgs.fromBundle(arguments!!).selectedProperty
//
        val viewModelFactory = OverviewViewModelFactory(marsProperty, application)

        binding.viewModel = ViewModelProvider(
                this, viewModelFactory).get(OverviewViewModel::class.java)
//         Allows Data Binding to Observe LiveData with the lifecycle of this Fragment


        // Giving the binding access to the OverviewViewModel
//        binding.viewModel = viewModel

        //8-12第十二步 Set binding.photosGrid.adapter to a new PhotoGridAdapter()
        binding.photosGrid.adapter = PhotoGridAdapter()
        //8-15第十步 Initialize PhotoGridAdapter with an OnClickListener that calls viewModel.displayPropertyDetails
//        binding.photosGrid.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
//            viewModel.displayPropertyDetails(it)
//        })

        //8-15第十三步 Observe navigateToSelectedProperty, Navigate when MarsProperty !null, then call displayPropertyDetailsComplete()
//        viewModel.navigateToSelectedProperty.observe(this, Observer {
//            if (null != it) {
//                this.findNavController().navigate(OverviewFragmentDirections.actionShowDetail(it))
//                viewModel.displayPropertyDetailsComplete()
//            }
//        })


        setHasOptionsMenu(true)
        return binding.root
    }

    /**
     * Inflates the overflow menu that contains filtering options.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //8-16第七步 override onOptionsItemSelected and have it call updateFilter on the viewmodel
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
            when (item.itemId) {
                R.id.show_rent_menu -> MarsApiFilter.SHOW_RENT
                R.id.show_buy_menu -> MarsApiFilter.SHOW_BUY
                else -> MarsApiFilter.SHOW_ALL
            }
        )
        return true
    }
}
