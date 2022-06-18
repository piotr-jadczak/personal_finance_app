import 'bootstrap/dist/css/bootstrap.css';
import ButtonAdd from '../ButtonAdd/ButtonAdd';
import axios from 'axios';
import { useState, useEffect } from 'react';
import StockForm from './StockForm/StockForm';
import StockUpdateForm from './StockUpdateForm/StockUpdateForm';
import Stock from './Stock/Stock';


const Stocks = (props) => {

  const jwtToken = props.jwtToken;
  const authHeader = {
    headers: { Authorization: `Bearer ${jwtToken}` }
  };
  const updateProfit = props.updateProfit
  const updateInvested = props.updateInvested
  const [stocks, setStocks] = useState([])
  const [availStocksData, setAvailStocksData] = useState([])
  const [showForm, setShowForm] = useState(false)
  const handleClose = () => setShowForm(false)
  const handleShow = () => setShowForm(true)

  const [showUpdateForm, setShowUpdateForm] = useState(false)
  const handleUpdateClose = () => setShowUpdateForm(false)
  const handleUpdateShow = () => setShowUpdateForm(true)
  const [updateId, setUpdateId] = useState(1)
  const [updateQuantity, setUpdateQuantity] = useState(0.01)
  const [updateAvgBought, setupdateAvgBought] = useState(1)
  const [updateStockName, setUpdateStockName] = useState("")


  useEffect(() => {
    const getStocks = async () => {
      const stocksFromServer = await fetchStocks()
      setStocks(stocksFromServer.data)
    }
    getStocks()
    getStocksData()
  }, [])

  const getStocksData = async () => {
    const stocksDataFromServer = await fetchStocksData()
    setAvailStocksData(stocksDataFromServer.data)
  }

  const removeStockData = (data) => {
    const filteredArray = availStocksData.filter((item) => item.id !== data.id)
    setAvailStocksData(filteredArray)
  }

  const onAdd = async (stock) => {
    const newStock = await addStock(stock)
    const newStockData = newStock.data
    removeStockData(newStockData.stockData)
    handleClose()
    setStocks([...stocks, newStockData])
  }

  const onDelete = async (id) => {
    const deleteResponse = await deleteStock(id)
    if(deleteResponse.data == true) {
      setStocks(stocks.filter((s) => s.id !== id))
      getStocksData()
    }
    else {
      alert('Error while deleting stock')
    }
  }

  const onUpdate = async (id, stock) => {
    const updateResponse = await updateStock(id, stock)
    if(updateResponse.status == 200) {
      const updStock = updateResponse.data
      setStocks(
        stocks.map((s) =>
          s.id === id ? { ...s, quantity: updStock.quantity, avgBought: updStock.avgBought } : s
        ))
    }
    else {
      alert('Error while deleting currency')
    }
    setShowUpdateForm(false)
  }

  const onUpdateForm = (id, stock) => {
    setUpdateQuantity(stock.quantity)
    setupdateAvgBought(stock.avgBought)
    setUpdateId(id)
    setUpdateStockName(stock.companyName)
    setShowUpdateForm(true)
  }

  const calculateProfit = () => {
    let sum = 0
    for (const el of stocks) {
      sum += Math.round((el.stockData.currentPrice-el.avgBought) * el.quantity * 100) / 100;
    }
    return sum
  }

  const calculateInvested = () => {
    let sum = 0
    for (const el of stocks) {
      sum += Math.round(el.avgBought * el.quantity * 100) / 100;
    }
    return sum
  }

  //fetch stocks
  const fetchStocks = () => {
    return axios.get("/savings/stocks",authHeader)
  }

  //fetch available stocks data
  const fetchStocksData = () => {
    return axios.get("/savings/available-stock-data",authHeader)
  }

  //add stock
  const addStock = async(stock) => {
    return axios.post("/savings/stocks", stock, authHeader)
  }

  //delete stock
  const deleteStock = async(id) => {
    return axios.delete(`/savings/stocks/${id}`, authHeader)
  }
  //update stock
  const updateStock = async(id, stock) => {
    return axios.put(`/savings/stocks/${id}`, stock, authHeader)
  }

  return (
    <div className="row pb-3 pt-3">
      {updateProfit(calculateProfit()) /* set stocks profit after render */}
      {updateInvested(calculateInvested()) /* set stocks invested after render */}
      { availStocksData.length > 0 && //show button if data can be added
        <div className="col-sm-3">
          <ButtonAdd description={"Add stock"} handleShow={handleShow} />
        </div>
      }
      {stocks.map(s => <Stock stock={s} key={s.id} onDelete={onDelete} onUpdateForm={onUpdateForm} />
      )}
      {showForm && <StockForm 
                      availStocksData={availStocksData} 
                      showFrom={handleShow}
                      handleClose={handleClose}
                      onAdd={onAdd}
                  />}
      {showUpdateForm && <StockUpdateForm
                            quantity={updateQuantity}
                            avgBought={updateAvgBought}
                            stockName={updateStockName}
                            id={updateId}
                            onUpdate={onUpdate}
                            showFrom={handleUpdateShow}
                            handleClose={handleUpdateClose}
                        />}                                     
    </div>
  )
}

export default Stocks