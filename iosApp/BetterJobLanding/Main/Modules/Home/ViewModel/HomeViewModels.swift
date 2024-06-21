//
//  HomeViewModels.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 06/06/2024.
//

import Foundation
import SwiftUI

enum JobPostFields {
    case style_1,style_2,style_3
    
    var info: (name:String,cellType:FormCellType) {
        switch self {
        case .style_1:
            return ("", .cellStyleThree)
        case .style_2:
            return ("", .cellStyleTwo)
        case .style_3:
            return ("", .cellStyleOne)
        }
    }
}

enum CompanyPostFields {
    case style_1
    
    var info: (name:String,cellType:FormCellType) {
        switch self {
        case .style_1:
            return ("", .cellStyleFour)
        }
    }
}

struct HomeViewModels {
    var jobPostsFormFields: [JobPostFields] = []
    var companyPostsFormFields: [CompanyPostFields] = []
}
