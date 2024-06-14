//
//  HomeViewModels.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 06/06/2024.
//

import Foundation
import SwiftUI

enum HomeFields {
    case searchTextBar,recentJobs,topJobsShortcut,topJobsOneDetail,topCompanies,topJobsTwoDetail,popularJobs,campaigns,suggestedHeader,suggestedForYou
    var info: (name:String,cellType:FormCellType) {
        switch self {
        case .searchTextBar:
            return ("",.searchTextBar)
        case .recentJobs:
            return ("",.cellStyleOne)
        case .topJobsShortcut:
            return ("",.cellStyleTwo)
        case .topJobsOneDetail:
            return ("",.cellStyleThree)
        case .topCompanies:
            return ("",.cellStyleFour)
        case .topJobsTwoDetail:
            return ("",.cellStyleFive)
        case .popularJobs:
            return ("",.cellStyleOne)
        case .campaigns:
            return ("",.cellStyleSix)
        case .suggestedHeader:
            return ("",.cellStyleHeader)
        case .suggestedForYou:
            return ("",.cellStyleSeven)
        }
    }
}

struct HomeViewModels {
    let formFields:[HomeFields] =
    [.searchTextBar,.recentJobs,.topJobsShortcut,.topJobsOneDetail,.topCompanies,.topJobsTwoDetail,.popularJobs,.campaigns,.suggestedHeader,.suggestedForYou]
   
}

