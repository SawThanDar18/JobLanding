//
//  HomeViewController+UITableView.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 06/06/2024.
//

import UIKit

extension HomeViewController: UITableViewDataSource, UITableViewDelegate {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.homeUIViewModel.count + 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        print("SECTION:: \(indexPath.section) \(indexPath.row)")
        
        let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: SearchTextFieldTableViewCell.self), for: indexPath) as! SearchTextFieldTableViewCell
        return cell
        
        if self.homeUIViewModel[indexPath.row].collectionType.uppercased() == HomeCollectionType.jobCollection.rawValue.uppercased(){
            
            //JOBS_POST
            if homeUIViewModel[indexPath.row].postStyle == StyleType.styleOne.rawValue{
                let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: CellStyleThreeTableViewCell.self), for: indexPath) as! CellStyleThreeTableViewCell
                cell.titleLabel.text = self.homeUIViewModel[indexPath.row].title
                cell.homeUIViewModel = self.homeUIViewModel
                cell.jobsListUIModel = self.jobsListUIModel
                cell.companyListModel = self.companyListModel
                return cell
            }else if homeUIViewModel[indexPath.row].postStyle == StyleType.styleTwo.rawValue{
                let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: CellStyleTwoTableViewCell.self), for: indexPath) as! CellStyleTwoTableViewCell
                cell.titleLabel.text = self.homeUIViewModel[indexPath.row].title
                cell.homeUIViewModel = self.homeUIViewModel
                cell.jobsListUIModel = self.jobsListUIModel
                cell.companyListModel = self.companyListModel
                return cell
            }else if homeUIViewModel[indexPath.row].postStyle == StyleType.styleThree.rawValue{
                let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: CellStyleOneTableViewCell.self), for: indexPath) as! CellStyleOneTableViewCell
                cell.selectionStyle = .none
                cell.titleLabel.text = self.homeUIViewModel[indexPath.row].title
                cell.homeUIViewModel = self.homeUIViewModel
                cell.jobsListUIModel = self.jobsListUIModel
                cell.companyListModel = self.companyListModel
                cell.layoutIfNeeded()
                return cell
            }
        }else  if self.homeUIViewModel[indexPath.row].collectionType.uppercased() == HomeCollectionType.companyCollection.rawValue.uppercased(){
            
            //COMPANIES_POST
            if homeUIViewModel[indexPath.row].postStyle == StyleType.styleOne.rawValue{
                let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: CellStyleFourTableViewCell.self), for: indexPath) as! CellStyleFourTableViewCell
                cell.titleLabel.text = self.homeUIViewModel[indexPath.row].title
                cell.homeUIViewModel = self.homeUIViewModel
                //                    cell.jobsListUIModel = self.jobsListUIModel
                cell.companyListModel = self.companyListModel
                return cell
            }
        }
        return UITableViewCell()
    }
}

