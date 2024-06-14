//
//  CellStyleSevenCollectionViewCell.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 10/06/2024.
//

import UIKit

class CellStyleSevenCollectionViewCell: UICollectionViewCell {

    @IBOutlet weak var mainView: UIView!
    @IBOutlet weak var companyImageView: UIImageView!
    @IBOutlet weak var alertImageView: UIImageView!
    @IBOutlet weak var positionLabel: UILabel!
    @IBOutlet weak var companyTitleLabel: UILabel!
    @IBOutlet weak var salaryLabel: UILabel!
    @IBOutlet weak var addressLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        setupCellStyleSeven()
        self.positionLabel.text = "Marketing Manager"
        self.companyTitleLabel.text = "G bank"
        self.salaryLabel.text = "MMK 400k-600k"
        self.addressLabel.text = "Yangon"
    }
    
    func setupCellStyleSeven(){
        self.mainView.layer.borderWidth = 1
        self.mainView.layer.borderColor = BHRJobLandingColors.bhrBJBorderColor.cgColor
        self.mainView.layer.cornerRadius = 8
    }

    func render(image:UIImage?,urlString:String,companyName:String,positionName:String,salary:String,township:String){
        if let image = image{
            companyImageView.image = image
            
        }else{
//            companyLogoImageView.setImage(with: URL(string: urlString))
        }
        
    }
}
