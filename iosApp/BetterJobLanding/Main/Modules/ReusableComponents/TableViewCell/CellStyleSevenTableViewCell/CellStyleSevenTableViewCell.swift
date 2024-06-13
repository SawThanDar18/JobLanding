//
//  CellStyleSevenTableViewCell.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 10/06/2024.
//

import UIKit

class CellStyleSevenTableViewCell: UITableViewCell {
    //    @IBOutlet weak var cellStyleSevenCollectionView: UICollectionView!
    //    @IBOutlet weak var titleLabel: UILabel!
    //    
    //    override func awakeFromNib() {
    //        super.awakeFromNib()
    //        setupCollectionView()
    //    }
    //    
    //    private func setupCollectionView() {
    //        cellStyleSevenCollectionView.dataSource = self
    //        cellStyleSevenCollectionView.delegate = self
    //        cellStyleSevenCollectionView.backgroundColor = .clear
    //        if let layout = cellStyleSevenCollectionView.collectionViewLayout as? UICollectionViewFlowLayout {
    //            layout.scrollDirection = .horizontal
    //            layout.minimumLineSpacing = 8
    //            layout.minimumInteritemSpacing = 0
    //        }
    //        cellStyleSevenCollectionView.registerForCells(
    //            String(describing: CellStyleSevenCollectionViewCell.self)
    //        )
    //        cellStyleSevenCollectionView.contentInset = UIEdgeInsets(top: 0, left: 0, bottom: 0, right: 0)
    //        cellStyleSevenCollectionView.isPagingEnabled = true
    //        cellStyleSevenCollectionView.isScrollEnabled = true
    //        cellStyleSevenCollectionView.showsVerticalScrollIndicator = false
    //        cellStyleSevenCollectionView.showsHorizontalScrollIndicator = false
    //        
    //    }
    //
    //    override func setSelected(_ selected: Bool, animated: Bool) {
    //        super.setSelected(selected, animated: animated)
    //
    //        // Configure the view for the selected state
    //    }
//}
    
    @IBOutlet weak var mainView: UIView!
    @IBOutlet weak var companyImageView: UIImageView!
    @IBOutlet weak var statusImageView: UIImageView!
    @IBOutlet weak var positionLabel: UILabel!
    @IBOutlet weak var companyTitleLabel: UILabel!
    @IBOutlet weak var salaryLabel: UILabel!
    @IBOutlet weak var addressLabel: UILabel!
    @IBOutlet weak var durationLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        setupCellStyleSeven()
        self.positionLabel.text = "Core Banking Specialist, Proximity Finance"
        self.companyTitleLabel.text = "G bank"
        self.salaryLabel.text = "MMK 400k-600k"
        self.addressLabel.text = "Yangon"
        self.durationLabel.text = "2 days left"
    }
    
    func setupCellStyleSeven(){
        self.mainView.bringSubviewToFront(statusImageView)
        
        self.positionLabel.font = .poppinsSemiBold(ofSize: 13)
        self.companyTitleLabel.font = .poppinsRegular(ofSize: 12)
        self.salaryLabel.font = .poppinsRegular(ofSize: 12)
        self.addressLabel.font = .poppinsRegular(ofSize: 12)
        self.durationLabel.font = .poppinsRegular(ofSize: 10)
        
        self.positionLabel.textColor = BHRJobLandingColors.bhrBJGray6A
        self.companyTitleLabel.textColor = BHRJobLandingColors.bhrBJGray75
        self.salaryLabel.textColor = BHRJobLandingColors.bhrBJGray75
        self.addressLabel.textColor = BHRJobLandingColors.bhrBJGray75
        self.durationLabel.textColor = BHRJobLandingColors.bhrBJWarningColor
        
        self.mainView.layer.borderWidth = 1
        self.mainView.layer.borderColor = BHRJobLandingColors.bhrBJBorderColor.cgColor
        self.mainView.layer.cornerRadius = 8
//        self.mainView.layer.masksToBounds = true
        
        }

    func render(image:UIImage?,urlString:String,companyName:String,positionName:String,salary:String,township:String){
        if let image = image{
            companyImageView.image = image
            
        }else{
//            companyLogoImageView.setImage(with: URL(string: urlString))
        }
        
    }
}

    

