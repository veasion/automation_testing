// 依赖Demo
// @author luozhuowei

let args = env.getOrDefault(auto.args, {
    name: 'veasion'
});
let result = {message: null};

result.message = 'hello ' + args.name;

env.putGlobal(auto.result, result);