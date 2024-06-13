//
//  CellStyleThreeCollectionViewCell.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 09/06/2024.
//

import UIKit

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
        
        self.timeLabel.textColor = .bhrGray75
        self.salaryLabel.textColor = .bhrGray75
        self.addressLabel.textColor = .bhrGray75
        
        self.cellStyleTwoMainView.layer.borderWidth = 1
        self.cellStyleTwoMainView.layer.borderColor = BHRJobLandingColors.bhrBJBorderColor.cgColor
        self.cellStyleTwoMainView.layer.cornerRadius = 8
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