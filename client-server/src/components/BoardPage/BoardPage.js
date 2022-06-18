import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.css';
import { useState, useEffect } from "react";
import Label from '../SavingsPage/Label/Label';
import Displayer from './Displayer';

export const BoardPage = (props) => {

  const jwtToken = props.jwtToken
  const authHeader = {
    headers: { Authorization: `Bearer ${jwtToken}` }
  }
  const [totalMoney, setTotalMoney] = useState(0)
  const [monthExpenses, setMonthExpenses] = useState(0)
  const [lastExpense, setLastExpenes] = useState("No expenses yet")
  const [bestInvestment, setBestInvestment] = useState("No profit yet")
  const [worstInvestment, setWorstInvestment] = useState("No loss yet")
  const [savingGoal, setSavingGoal] = useState("No goals yet")

  const month = ["January","February","March","April","May","June",
                  "July","August","September","October","November","December"];
  const currentMonth = month[new Date().getMonth()];

  useEffect(() => {
    const getTotalMoney = async () => {
      const totalMoney = await fetchTotalMoney()
      setTotalMoney(`${totalMoney.data} zł`)
    }
    const getTotalExpenses = async () => {
      const today = new Date()
      const month = today.getMonth() + 1;
      const year = today.getFullYear();
      const totalExpenses = await fetchTotalExpenses(month, year)
      setMonthExpenses(`${totalExpenses.data} zł`)
    }
    const getLastExpense = async () => {
      const lastExpense = await fetchLastExpense()
      if(lastExpense.status === 200) {
        setLastExpenes(`${lastExpense.data.amount} zł`)      
      }
      else {
        setLastExpenes("No expenses yet")
      }
    }
    const getBestInvestment = async () => {
      const bestInvestment = await fetchBestInvestment()
      if(bestInvestment.status === 200) {
        setBestInvestment(`${bestInvestment.data.toFixed(2)} zł`)
      }
      else {
        setBestInvestment("No profit yet")
      }
    }
    const getWorstInvestment = async () => {
      const worstInvestment = await fetchWorstInvestment()
      if(worstInvestment.status === 200) {
        setWorstInvestment(`${worstInvestment.data.toFixed(2)} zł`)
      }
      else {
        setWorstInvestment("No loss yet")
      }
    }
    const getMostCompletedGoal = async () => {
      const savingGoal = await fetchMostCompletedGoal()
      if(savingGoal.status === 200) {
        setSavingGoal(`${savingGoal.data.description} (${savingGoal.data.percentCompleted}%)`)
      }
      else {
        setSavingGoal("No goals yet")
      }
    }
    getTotalMoney()
    getTotalExpenses()
    getLastExpense()
    getBestInvestment()
    getWorstInvestment()
    getMostCompletedGoal()
    
  }, [])

  // fetch total money
  const fetchTotalMoney = () => {
    return axios.get("/board/total-money",authHeader)
  }
  // fetch month total expenses
  const fetchTotalExpenses = (month, year) => {
    return axios.get(`/board/total-expenses/month=${month}&year=${year}`,authHeader)
  }
  // fetch last expenses
  const fetchLastExpense = () => {
    return axios.get("/board/last-expense",authHeader)
  }
  // fetch best investment
  const fetchBestInvestment = () => {
    return axios.get("/board/max-profit-investment",authHeader)
  }
  // fetch worst investment
  const fetchWorstInvestment = () => {
    return axios.get("/board/max-loss-investment",authHeader)
  }
  // fetch most completed saving goal
  const fetchMostCompletedGoal = () => {
    return axios.get("/board/most-completed-goal",authHeader)
  }

  return (
    <div className="p-2">
      <Label title={"Board"} />
      <div className="row pb-5 pt-5">
        <div className="col-sm-4">
          <Displayer description={"Total money"} money={totalMoney} />
        </div>
        <div className="col-sm-4">
          <Displayer description={`Total expenses (${currentMonth})`} money={monthExpenses} />
        </div>
        <div className="col-sm-4">
          <Displayer description={"Last expense"} money={lastExpense} />
        </div>
      </div>
      <div className="row pb-2 pt-5">
        <div className="col-sm-4">
          <Displayer description={"Best investment (profit)"} money={bestInvestment} />
        </div>
        <div className="col-sm-4">
          <Displayer description={"Worst investment (loss)"} money={worstInvestment} />
        </div>
        <div className="col-sm-4">
          <Displayer description={"Closest saving goal"} money={savingGoal} /> 
        </div>
      </div>
    </div>
  )

}

export default BoardPage
