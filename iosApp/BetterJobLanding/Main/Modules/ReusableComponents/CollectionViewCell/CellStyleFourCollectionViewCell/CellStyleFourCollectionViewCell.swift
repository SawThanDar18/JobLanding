//
//  CellStyleFourCollectionViewCell.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 09/06/2024.
//

import UIKit
import shared

class CellStyleFourCollectionViewCell: UICollectionViewCell {

    @IBOutlet weak var cellStyleFourMainView: UIView!
    @IBOutlet weak var companyLogoImageView: UIImageView!
    @IBOutlet weak var companyTitleLabel: UILabel!
    @IBOutlet weak var openingLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        setupCellStyleFour()
        self.companyTitleLabel.text = "Yoma strategic holdings Ltd."
        self.openingLabel.text = "8 opening"
    }
    
    func setupCellStyleFour(){
        self.companyTitleLabel.font = .poppinsMedium(ofSize: 14)
        self.openingLabel.font = .poppinsLight(ofSize: 12)
        
        self.companyTitleLabel.textColor = BHRJobLandingColors.bhrBJGray45
        self.openingLabel.textColor = BHRJobLandingColors.bhrBJGray45
        
        self.cellStyleFourMainView.layer.borderWidth = 1
        self.cellStyleFourMainView.layer.borderColor = BHRJobLandingColors.bhrBJBorderColor.cgColor
        self.cellStyleFourMainView.layer.cornerRadius = 8
    }

    func renderOfCompanyList(companyUIModel: CollectionCompaniesUIModel){
        self.openingLabel.text = "\(companyUIModel.jobOpeningCount) openings"
        self.companyTitleLabel.text = companyUIModel.company.name
    }
}
