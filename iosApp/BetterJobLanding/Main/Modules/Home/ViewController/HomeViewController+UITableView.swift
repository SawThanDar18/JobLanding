//
//  HomeViewController+UITableView.swift
//  BetterJobLanding
//
//  Created by Myint Zu on 06/06/2024.
//

import UIKit

extension HomeViewController: UITableViewDataSource, UITableViewDelegate {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return viewModel.formFields.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
            let homeformField = viewModel.formFields[indexPath.row]
            switch homeformField.info.cellType {
            case .searchTextBar:
                let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: SearchTextFieldTableViewCell.self), for: indexPath) as! SearchTextFieldTableViewCell
                cell.selectionStyle = .none
                switch homeformField {
                case .searchTextBar:
                    return cell
                default:
                    
                    break
                }
                cell.layoutIfNeeded()
                return cell
            case .cellStyleOne:
                let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: CellStyleOneTableViewCell.self), for: indexPath) as! CellStyleOneTableViewCell
                cell.selectionStyle = .none
                switch homeformField {
                case .recentJobs:
                    cell.titleLabel.text = "Recent jobs"
                case .popularJobs:
                    cell.titleLabel.text = "Popular jobs"
                default:
                    
                    break
                }
                cell.layoutIfNeeded()
                return cell
            case .cellStyleTwo:
                let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: CellStyleTwoTableViewCell.self), for: indexPath) as! CellStyleTwoTableViewCell
                switch homeformField {
                case .topJobsShortcut:
                    cell.titleLabel.text = "Top Designer jobs"
                default:
                    break
                }
                
                return cell
            case .cellStyleThree:
                let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: CellStyleThreeTableViewCell.self), for: indexPath) as! CellStyleThreeTableViewCell
                switch homeformField {
                case .topJobsOneDetail:
                    cell.titleLabel.text = "Top Developer jobs"
                default:
                    break
                }
                
                return cell
            case .cellStyleFour:
                let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: CellStyleFourTableViewCell.self), for: indexPath) as! CellStyleFourTableViewCell
                switch homeformField {
                case .topCompanies:
                    cell.titleLabel.text = "Top companies"
                default:
                    break
                }
                
                return cell
            case .cellStyleFive:
                let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: CellStyleFiveTableViewCell.self), for: indexPath) as! CellStyleFiveTableViewCell
                switch homeformField {
                case .topJobsTwoDetail:
                    cell.titleLabel.text = "Top Designer jobs"
                default:
                    break
                }
                
                return cell
            case .cellStyleSix:
                let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: CellStyleSixTableViewCell.self), for: indexPath) as! CellStyleSixTableViewCell
                switch homeformField {
                case .campaigns:
                    cell.titleLabel.text = "Campaigns"
                default:
                    break
                }
                return cell
            case .cellStyleHeader:
                let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: CellStyleHeaderTableViewCell.self), for: indexPath) as! CellStyleHeaderTableViewCell
                switch homeformField {
                case .suggestedHeader:
                    cell.titleLabel.text = "Suggested For You"
                default:
                    break
                }
                return cell
            case .cellStyleSeven:
                let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: CellStyleSevenTableViewCell.self), for: indexPath) as! CellStyleSevenTableViewCell
                cell.selectionStyle = .none
                switch homeformField {
                case .suggestedForYou:
                    break
//                    cell.titleLabel.text = "Suggested For You"
                default:
                    break
                }
                return cell
            default:
                return UITableViewCell()
            }
        }
}

