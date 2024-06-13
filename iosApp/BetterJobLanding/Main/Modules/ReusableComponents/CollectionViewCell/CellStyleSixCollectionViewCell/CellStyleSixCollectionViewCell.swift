//
//  CellStyleSixCollectionViewCell.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 09/06/2024.
//

import UIKit

class CellStyleSixCollectionViewCell: UICollectionViewCell {

    @IBOutlet weak var campaignImageView: UIImageView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        setupCellStyleSix()
    }
    
    func setupCellStyleSix(){
        self.campaignImageView.layer.borderWidth = 0
        self.campaignImageView.layer.borderColor = UIColor.clear.cgColor
        self.campaignImageView.layer.cornerRadius = 8
    }

    func render(image:UIImage?,urlString:String,companyName:String,positionName:String,salary:String,township:String){
        if let image = image{
            campaignImageView.image = image
            
        }else{
//            companyLogoImageView.setImage(with: URL(string: urlString))
        }
        
    }
}
