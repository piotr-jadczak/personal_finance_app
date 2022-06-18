import Label from "./Label/Label";
import MoneyDisplayer from "./MoneyDisplayer/MoneyDisplayer";
import 'bootstrap/dist/css/bootstrap.css';
import ProfitDisplayer from "./ProfitDisplayer/ProfitDisplayer";
import CashDisplayer from "./CashDisplayer/CashDisplayer";
import Currencies from "./Currencies/Currencies";
import Stocks from "./Stocks/Stocks";
import { useState, useEffect } from 'react';

const SavingsPage = (props) => {

  const jwtToken = props.jwtToken
  const [cash, setCash] = useState(0)
  const handleCashUpdate = (newCash) => setCash(newCash)
  const handleGetCash = () => cash
  const [currProfit, setCurrProfit] = useState(0.0)
  const handleCurrProfitUpdate = (newProfit) => setCurrProfit(newProfit)
  const [currInvested, setCurrInvested] = useState(0.0)
  const handleCurrInvestedtUpdate = (newInvested) => setCurrInvested(newInvested)
  const [stockProfit, setStockProfit] = useState(0.0)
  const handleStockProfitUpdate = (newProfit) => setStockProfit(newProfit)
  const [stockInvested, setStockInvested] = useState(0.0)
  const handleStockInvestedtUpdate = (newInvested) => setStockInvested(newInvested)

  const totalInvested = () => (currInvested + stockInvested)
  const totalProfit = () => (currProfit + stockProfit)
  const profitPercent = () => (totalInvested() > 0.0 ? (totalProfit() / totalInvested() * 100) : 0)
  const totalMoney = () => (cash + totalInvested() + totalProfit())

  return (
    <div className="p-2">
      <Label title={"Summary"} />
      <div className="row pb-5">
        <div className="col-sm-4">
          <MoneyDisplayer money={totalMoney()} description={"Total"} />
        </div>
        <div className="col-sm-4">
          <ProfitDisplayer profit={totalProfit()} description={"Profit"} profitPercent={profitPercent()} />
        </div>
        <div className="col-sm-4">
          <CashDisplayer description={"Cash"} jwtToken={jwtToken} cash={handleGetCash} setCash={handleCashUpdate} />
        </div>
      </div>
      <Label title={"Currencies"} />
      <Currencies jwtToken={jwtToken} updateProfit={handleCurrProfitUpdate} updateInvested={handleCurrInvestedtUpdate} />
      <Label title={"Stocks"} />
      <Stocks jwtToken={jwtToken} updateProfit={handleStockProfitUpdate} updateInvested={handleStockInvestedtUpdate} />
    </div>
  )
}

export default SavingsPage