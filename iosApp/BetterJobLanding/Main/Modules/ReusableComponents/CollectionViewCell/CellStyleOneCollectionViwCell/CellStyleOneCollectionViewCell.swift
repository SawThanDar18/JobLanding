//
//  CellStyleOneCollectionViewCell.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 07/06/2024.
//

import UIKit

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

    func render(image:UIImage?,urlString:String,companyName:String,positionName:String,salary:String,township:String){
        if let image = image{
            companyLogoImageView.image = image
            
        }else{
//            companyLogoImageView.setImage(with: URL(string: urlString))
        }
        
    }
}
