//
//  CellStyleOneCollectionViewCell.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 07/06/2024.
//

import UIKit
import shared

class CellStyleOneCollectionViewCell: UICollectionViewCell {
    @IBOutlet weak var cellStyleOneMainView: UIView!
    @IBOutlet weak var companyLogoImageView: UIImageView!
    @IBOutlet weak var companyNameLabel: UILabel!
    @IBOutlet weak var positionLabel: UILabel!
    @IBOutlet weak var salaryLabel: UILabel!
    @IBOutlet weak var addressLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        self.setupCellStyleOne()
        self.companyNameLabel.text = "Yoma strategic holding Ltd."
        self.positionLabel.text = "Product Designer"
        self.salaryLabel.text = "300k - 400k"
        self.addressLabel.text = "Yangon,Myanmar"
    }
    
    func setupCellStyleOne(){
        self.companyNameLabel.font = .poppinsMedium(ofSize: 12)
        self.positionLabel.font = .poppinsSemiBold(ofSize: 14)
        self.salaryLabel.font = .poppinsRegular(ofSize: 12)
        self.addressLabel.font = .poppinsRegular(ofSize: 12)
        
        self.companyNameLabel.textColor = BHRJobLandingColors.bhrBJGray6A
        self.positionLabel.textColor = BHRJobLandingColors.bhrBJGrayAA
        self.salaryLabel.textColor = BHRJobLandingColors.bhrBJGray75
        self.addressLabel.textColor = BHRJobLandingColors.bhrBJGray75
        
        self.cellStyleOneMainView.layer.borderWidth = 1
        self.cellStyleOneMainView.layer.borderColor = BHRJobLandingColors.bhrBJBorderColor.cgColor
        self.cellStyleOneMainView.layer.cornerRadius = 8
    }
    
    func renderOfJobsList(jobUIModel: JobsListUIModel){
        self.positionLabel.text = jobUIModel.position
        self.companyNameLabel.text = jobUIModel.company.name
        self.salaryLabel.text = "\(jobUIModel.miniSalary) - \(jobUIModel.maxiSalary) \(jobUIModel.stateName)"
    }
}
