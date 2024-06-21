//
//  CellStyleThreeCollectionViewCell.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 09/06/2024.
//

import UIKit
import shared

class CellStyleThreeCollectionViewCell: UICollectionViewCell {
    @IBOutlet weak var cellStyleTwoMainView: UIView!
    @IBOutlet weak var companyLogoImageView: UIImageView!
    @IBOutlet weak var companyTitleLabel: UILabel!
    @IBOutlet weak var positionLabel: UILabel!
    @IBOutlet weak var timeLabel: UILabel!
    @IBOutlet weak var salaryLabel: UILabel!
    @IBOutlet weak var addressLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        setupCellStyleThree()
        self.companyTitleLabel.text = "Myanmar Now Company Limited"
        self.positionLabel.text = "Front-end Developer"
        self.timeLabel.text = "Full time"
        self.salaryLabel.text = "MMK 300K-400K"
        self.addressLabel.text = "Yangon,Myanmar"
    }
    
    func setupCellStyleThree(){
        self.companyTitleLabel.font = .poppinsRegular(ofSize: 14)
        self.positionLabel.font = .poppinsSemiBold(ofSize: 14)
        self.timeLabel.font = .poppinsRegular(ofSize: 12)
        self.salaryLabel.font = .poppinsRegular(ofSize: 12)
        self.addressLabel.font = .poppinsRegular(ofSize: 12)
        
        self.companyTitleLabel.textColor = BHRJobLandingColors.bhrBJGray6A
        self.positionLabel.textColor = BHRJobLandingColors.bhrBJGrayAA
        
        self.timeLabel.textColor = BHRJobLandingColors.bhrBJGray75
        self.salaryLabel.textColor = BHRJobLandingColors.bhrBJGray75
        self.addressLabel.textColor = BHRJobLandingColors.bhrBJGray75
        
        self.cellStyleTwoMainView.layer.borderWidth = 1
        self.cellStyleTwoMainView.layer.borderColor = BHRJobLandingColors.bhrBJBorderColor.cgColor
        self.cellStyleTwoMainView.layer.cornerRadius = 8
        self.companyLogoImageView.layer.cornerRadius = 8
    }
    
    func renderOfJobsList(jobUIModel: JobsListUIModel){
        self.companyTitleLabel.text = jobUIModel.company.name
        self.positionLabel.text = jobUIModel.position
        self.timeLabel.text = jobUIModel.employmentType
        self.salaryLabel.text = "\(jobUIModel.currencyCode) \(jobUIModel.miniSalary) \(jobUIModel.maxiSalary) "
        self.addressLabel.text = jobUIModel.stateName
    }
}
