//
//  CellStyleFiveCollectionViewCell.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 09/06/2024.
//

import UIKit

class CellStyleFiveCollectionViewCell: UICollectionViewCell {
    @IBOutlet weak var cellStyleFiveMainView: UIView!
    @IBOutlet weak var companyLogoImageView: UIImageView!
    @IBOutlet weak var companyTitleLabel: UILabel!
    @IBOutlet weak var positionLabel: UILabel!
    @IBOutlet weak var timeLabel: UILabel!
    @IBOutlet weak var salaryLabel: UILabel!
    @IBOutlet weak var townshipLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        setupCellStyleFive()
        self.companyTitleLabel.text = "MI"
        self.positionLabel.text = "Graphic Designer"
        self.timeLabel.text = "Full time"
        self.salaryLabel.text = "300K-400K"
        self.townshipLabel.text = "Yangon,Myanmar"
    }
    
    func setupCellStyleFive(){
        self.companyTitleLabel.font = .poppinsRegular(ofSize: 14)
        self.positionLabel.font = .poppinsSemiBold(ofSize: 14)
        self.timeLabel.font = .poppinsRegular(ofSize: 12)
        self.salaryLabel.font = .poppinsRegular(ofSize: 12)
        self.townshipLabel.font =  .poppinsRegular(ofSize: 12)
        
        self.companyTitleLabel.textColor = BHRJobLandingColors.bhrBJGray6A
        self.positionLabel.textColor = BHRJobLandingColors.bhrBJGray75
        self.timeLabel.textColor = BHRJobLandingColors.bhrBJGray75
        self.salaryLabel.textColor = BHRJobLandingColors.bhrBJGray75
        self.townshipLabel.textColor = BHRJobLandingColors.bhrBJGray75
        
        self.cellStyleFiveMainView.layer.borderWidth = 1
        self.cellStyleFiveMainView.layer.borderColor = BHRJobLandingColors.bhrBJBorderColor.cgColor
        self.cellStyleFiveMainView.layer.cornerRadius = 8
        self.companyLogoImageView.layer.cornerRadius = 8
    }

    func render(image:UIImage?,urlString:String,companyName:String,positionName:String,salary:String,township:String){
        if let image = image{
            companyLogoImageView.image = image
            
        }else{
//            companyLogoImageView.setImage(with: URL(string: urlString))
        }
        
    }
}
