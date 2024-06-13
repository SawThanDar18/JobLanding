//
//  CellStyleTwoCollectionViewCell.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 09/06/2024.
//

import UIKit

class CellStyleTwoCollectionViewCell: UICollectionViewCell {
    @IBOutlet weak var cellStyleTwoMainView: UIView!
    @IBOutlet weak var companyLogoImageView: UIImageView!
    @IBOutlet weak var positionLabel: UILabel!
    @IBOutlet weak var companyTitleLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        setupCellStyleTwo()
        self.positionLabel.text = "Graphic Designer"
        self.companyTitleLabel.text = "Amazon"
        
    }
    
    func setupCellStyleTwo(){
        self.positionLabel.font = .poppinsSemiBold(ofSize: 12)
        self.companyTitleLabel.font = .poppinsRegular(ofSize: 12)
        
        self.companyTitleLabel.textColor = BHRJobLandingColors.bhrBJGrayAA
        self.positionLabel.textColor = BHRJobLandingColors.bhrBJGrayAA
        
        self.companyLogoImageView.layer.cornerRadius = 8
        self.cellStyleTwoMainView.layer.borderWidth = 1
        self.cellStyleTwoMainView.layer.borderColor = BHRJobLandingColors.bhrBJBorderColor.cgColor
        self.cellStyleTwoMainView.layer.cornerRadius = 8
    }

    func render(image:UIImage?,urlString:String,companyName:String,positionName:String,salary:String,township:String){
        if let image = image{
            companyLogoImageView.image = image
            
        }else{
//            companyLogoImageView.setImage(with: URL(string: urlString))
        }
        
    }
}
